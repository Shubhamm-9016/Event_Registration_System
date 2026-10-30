package com.event;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9+()\\- ]{7,15}$");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventIdText = request.getParameter("eventId");
        int eventId;
        try {
            eventId = Integer.parseInt(eventIdText);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/events");
            return;
        }
        String sql = "SELECT event_id, event_name, description, event_date, venue, image_url FROM events WHERE event_id = ?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    response.sendRedirect(request.getContextPath() + "/events");
                    return;
                }
                java.util.Map<String, Object> event = new java.util.HashMap<>();
                event.put("eventId", rs.getInt("event_id"));
                event.put("eventName", rs.getString("event_name"));
                event.put("description", rs.getString("description"));
                event.put("eventDate", rs.getDate("event_date"));
                event.put("venue", rs.getString("venue"));
                event.put("imageUrl", rs.getString("image_url"));
                request.setAttribute("event", event);
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            getServletContext().log("Unable to load selected event", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Could not load this event. Please try again later.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String eventIdText = trim(request.getParameter("eventId"));
        String name = trim(request.getParameter("name"));
        String email = trim(request.getParameter("email"));
        String phone = trim(request.getParameter("phone"));

        String error = null;
        int eventId = -1;
        try { eventId = Integer.parseInt(eventIdText); } catch (NumberFormatException ignored) { error = "Please select a valid event."; }
        if (error == null && (name.isEmpty() || name.length() > 100)) error = "Please enter your name (up to 100 characters).";
        if (error == null && (email.isEmpty() || email.length() > 150 || !EMAIL_PATTERN.matcher(email).matches())) error = "Please enter a valid email address.";
        if (error == null && (phone.isEmpty() || !PHONE_PATTERN.matcher(phone).matches())) error = "Please enter a valid phone number (7–15 characters).";

        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("eventId", eventIdText);
            request.setAttribute("name", name);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        String checkSql = "SELECT event_id, event_name, event_date, venue FROM events WHERE event_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement check = con.prepareStatement(checkSql)) {
            check.setInt(1, eventId);
            String eventName;
            String eventDate;
            String venue;
            try (ResultSet rs = check.executeQuery()) {
                if (!rs.next()) {
                    request.setAttribute("error", "That event could not be found. Please choose an event from the home page.");
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                    return;
                }
                eventName = rs.getString("event_name");
                eventDate = rs.getDate("event_date").toString();
                venue = rs.getString("venue");
            }

            String insertSql = "INSERT INTO `register` (event_id, name, email, phone) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insert = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insert.setInt(1, eventId);
                insert.setString(2, name);
                insert.setString(3, email);
                insert.setString(4, phone);
                insert.executeUpdate();
                try (ResultSet keys = insert.getGeneratedKeys()) {
                    if (keys.next()) {
                        request.setAttribute("registrationId", keys.getLong(1));
                        request.setAttribute("name", name);
                        request.setAttribute("email", email);
                        request.setAttribute("phone", phone);
                        request.setAttribute("eventName", eventName);
                        request.setAttribute("eventDate", eventDate);
                        request.setAttribute("venue", venue);
                        request.getRequestDispatcher("/success.jsp").forward(request, response);
                        return;
                    }
                }
            }
            throw new ServletException("Registration was saved, but its generated ID could not be retrieved.");
        } catch (ServletException e) {
            throw e;
        } catch (Exception e) {
            getServletContext().log("Unable to save registration", e);
            request.setAttribute("error", "We could not save your registration. Please check the database connection and try again.");
            request.setAttribute("eventId", eventIdText);
            request.setAttribute("name", name);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

    private String trim(String value) { return value == null ? "" : value.trim(); }
}
