package com.Hafizh.backend.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HealthController {

    @GetMapping("/health")
    public Map<String, Object> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Jetpack Joyride Backend is running!");
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }

    @GetMapping("/info")
    public Map<String, Object> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "CS6_Hafizh_Backend");
        response.put("version", "1.0");
        response.put("description", "Backend for Jetpack Joyride Case Study");

        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("players", "/api/players");
        endpoints.put("scores", "/api/scores");
        endpoints.put("leaderboard", "/api/scores/leaderboard");
        endpoints.put("health", "/api/health");

        response.put("endpoints", endpoints);
        return response;
    }
}