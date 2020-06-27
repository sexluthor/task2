package ru.burlakov.alfa.task2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/admin/health")
    public String health() {
        return "{\"status\":\"UP\"}";
    }
}
