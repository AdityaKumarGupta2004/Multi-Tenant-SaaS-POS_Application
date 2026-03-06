package com.trisys.Pos.System.controller;

import com.trisys.Pos.System.payload.response.ApiResponse;
import com.trisys.Pos.System.service.SuperAdminServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/super-admin")
@RequiredArgsConstructor
public class SuperAdminController {

    private final SuperAdminServices superAdminServices;

    /**
     * Dashboard summary
     */
    @GetMapping("/dashboard/summary")
    public ResponseEntity<ApiResponse> getDashboardSummary() {

        ApiResponse response = ApiResponse.success(
                superAdminServices.getDashboardSummary()
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Store registrations last 7 days
     */
    @GetMapping("/dashboard/store-registrations")
    public ResponseEntity<ApiResponse> getStoreRegistrations() {

        ApiResponse response = ApiResponse.success(
                superAdminServices.getStoreRegistrationsLast7Days()
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Store status distribution
     */
    @GetMapping("/dashboard/store-status-distribution")
    public ResponseEntity<ApiResponse> getStoreStatusDistribution() {

        ApiResponse response = ApiResponse.success(
                superAdminServices.getStoreStatusDistribution()
        );

        return ResponseEntity.ok(response);
    }
}