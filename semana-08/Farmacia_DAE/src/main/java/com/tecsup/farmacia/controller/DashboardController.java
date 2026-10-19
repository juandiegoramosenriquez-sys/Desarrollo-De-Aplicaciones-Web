package com.tecsup.farmacia.controller;

import com.tecsup.farmacia.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerDashboard() {
        return ResponseEntity.ok(
                dashboardService.obtenerIndicadores()
        );
    }
}