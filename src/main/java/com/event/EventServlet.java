package com.event;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/events")
public class EventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String, Object>> events = new ArrayList<>();
        String sql = "SELECT event_id, event_name, description, event_date, venue, image_url FROM events ORDER BY event_date ASC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> event = new HashMap<>();
                event.put("eventId", rs.getInt("event_id"));
                event.put("eventName", rs.getString("event_name"));
                event.put("description", rs.getString("description"));
                event.put("eventDate", rs.getDate("event_date"));
                event.put("venue", rs.getString("venue"));
                event.put("imageUrl", rs.getString("image_url"));
                events.add(event);
            }
            request.setAttribute("events", events);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception e) {
            getServletContext().log("Unable to load events", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Could not load events. Check that MySQL is running and DBConnection.java has the correct password.");
        }
    }
}
