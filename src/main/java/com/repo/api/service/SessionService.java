package com.repo.api.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    public String createSession(HttpSession session){
        session.setAttribute("username", "guest");
        // Retrieve and return the session ID
        return session.getId();
    }

    public String getSession(HttpSession session) {
        // Get the session attribute (username)
        String username = (String) session.getAttribute("username");

        // If no session exists, return an error message
        if (username == null) {
            return "No session found!";
        }

        // Return session data to the client
        return "Session found with username: " + username;
    }

    public String invalidateSession(HttpSession session) {
        // Invalidate the session
        session.invalidate();
        return "Session invalidated!";
    }
}
