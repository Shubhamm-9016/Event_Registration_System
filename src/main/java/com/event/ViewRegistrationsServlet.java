package com.event;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registrations")
public class ViewRegistrationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String, Object>> registrations = new ArrayList<>();
        String sql = "SELECT r.registration_id, r.name, r.email, r.phone, r.registration_date, e.event_name, e.event_date, e.venue " +
                     "FROM `register` r INNER JOIN events e ON r.event_id = e.event_id ORDER BY r.registration_date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("registrationId", rs.getLong("registration_id"));
                row.put("name", rs.getString("name"));
                row.put("email", rs.getString("email"));
                row.put("phone", rs.getString("phone"));
                row.put("registrationDate", rs.getTimestamp("registration_date"));
                row.put("eventName", rs.getString("event_name"));
                row.put("eventDate", rs.getDate("event_date"));
                row.put("venue", rs.getString("venue"));
                registrations.add(row);
            }
            request.setAttribute("registrations", registrations);
            request.getRequestDispatcher("/registrations.jsp").forward(request, response);
        } catch (Exception e) {
            getServletContext().log("Unable to load registrations", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Could not load registrations. Check that MySQL is running and DBConnection.java has the correct password.");
        }
    }
}
