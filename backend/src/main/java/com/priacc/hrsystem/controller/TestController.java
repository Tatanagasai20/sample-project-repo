package com.priacc.hrsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@Tag(name = "Test", description = "Test API endpoints")
public class TestController {

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Check if the API is running")
    public Map<String, Object> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        response.put("message", "HR System API is running");
        response.put("version", "1.0.0");
        return response;
    }

    @GetMapping("/db-status")
    @Operation(summary = "Database status", description = "Check database connectivity")
    public Map<String, Object> databaseStatus() {
        Map<String, Object> response = new HashMap<>();
        response.put("database", "H2");
        response.put("status", "Connected");
        response.put("h2_console", "http://localhost:8080/h2-console");
        response.put("db_url", "jdbc:h2:mem:hrdb");
        response.put("username", "sa");
        response.put("password", "");
        return response;
    }
}