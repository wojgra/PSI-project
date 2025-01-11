package com.example.scheduleservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule-service/test")
public class TestController {

    @GetMapping("/public")
    public String publicAccess() {
        return "Public Content.";
    }

    @PreAuthorize("hasRole('CLIENT') or hasRole('TICKET_COLLECTOR') or hasRole('ADMIN')")
    @GetMapping("/client")
    public String clientAccess() {
        return "Client Content.";
    }

    @PreAuthorize("hasRole('TICKET_COLLECTOR') or hasRole('ADMIN')")
    @GetMapping("/ticket-collector")
    public String ticketCollectorAccess() {
        return "Ticket Collector Content.";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminAccess() {
        return "Admin Content.";
    }
}
