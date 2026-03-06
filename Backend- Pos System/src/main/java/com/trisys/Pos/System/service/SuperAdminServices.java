package com.trisys.Pos.System.service;

import java.util.Map;

public interface SuperAdminServices {

    Map<String, Object> getDashboardSummary();

    Map<String, Object> getStoreRegistrationsLast7Days();

    Map<String, Object> getStoreStatusDistribution();

}