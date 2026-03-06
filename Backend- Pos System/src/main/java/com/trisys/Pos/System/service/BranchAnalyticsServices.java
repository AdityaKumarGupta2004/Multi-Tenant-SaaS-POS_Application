package com.trisys.Pos.System.service;

import java.util.Map;

public interface BranchAnalyticsServices {

    Map<String, Object> getDailySales(int days);

    Map<String, Object> getTopProducts();

    Map<String, Object> getTopCashiers();

    Map<String, Object> getCategorySales(String date);

    Map<String, Object> getTodayOverview();

    Map<String, Object> getPaymentBreakdown(String date);
}
