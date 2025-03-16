package com.repo.api.controller;

import com.repo.api.dto.SessionResponse;
import com.repo.api.service.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    // Create a session and store an attribute
    @GetMapping("/create")
    public ResponseEntity<SessionResponse> createSession(HttpSession session) {
       return ResponseEntity.ok(new SessionResponse(sessionService.createSession(session)));
    }

    // Retrieve session attribute
    @GetMapping("/get")
    public String getSession(HttpSession session) {
       return sessionService.getSession(session);
    }

    // Invalidate the session
    @GetMapping("/invalidate")
    public String invalidateSession(HttpSession session) {
        return sessionService.invalidateSession(session);
    }
}
