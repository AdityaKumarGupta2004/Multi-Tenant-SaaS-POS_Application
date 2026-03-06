package com.trisys.Pos.System.controller;

import com.trisys.Pos.System.payload.response.ApiResponse;
import com.trisys.Pos.System.service.BranchAnalyticsServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/branch-analytics")
@RequiredArgsConstructor
public class BranchAnalyticsController {

    private final BranchAnalyticsServices branchAnalyticsService;

    /**
     * Daily sales for last N days
     */
    @GetMapping("/daily-sales")
    public ResponseEntity<ApiResponse> getDailySales(
            @RequestParam(defaultValue = "7") int days) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        branchAnalyticsService.getDailySales(days)
                )
        );
    }

    /**
     * Top 5 selling products
     */
    @GetMapping("/top-products")
    public ResponseEntity<ApiResponse> getTopProducts() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        branchAnalyticsService.getTopProducts()
                )
        );
    }

    /**
     * Top 5 cashiers by revenue
     */
    @GetMapping("/top-cashiers")
    public ResponseEntity<ApiResponse> getTopCashiers() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        branchAnalyticsService.getTopCashiers()
                )
        );
    }

    /**
     * Category-wise sales for a specific date
     */
    @GetMapping("/category-sales")
    public ResponseEntity<ApiResponse> getCategorySales(
            @RequestParam String date) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        branchAnalyticsService.getCategorySales(date)
                )
        );
    }

    /**
     * Today's branch overview
     */
    @GetMapping("/today-overview")
    public ResponseEntity<ApiResponse> getTodayOverview() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        branchAnalyticsService.getTodayOverview()
                )
        );
    }

    /**
     * Payment breakdown for a specific date
     */
    @GetMapping("/payment-breakdown")
    public ResponseEntity<ApiResponse> getPaymentBreakdown(
            @RequestParam String date) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        branchAnalyticsService.getPaymentBreakdown(date)
                )
        );
    }
}