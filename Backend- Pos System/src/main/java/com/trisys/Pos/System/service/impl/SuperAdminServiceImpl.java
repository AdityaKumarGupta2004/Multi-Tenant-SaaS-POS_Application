package com.trisys.Pos.System.service.impl;

import com.trisys.Pos.System.domain.StoreStatus;
import com.trisys.Pos.System.modal.Store;
import com.trisys.Pos.System.repository.StoreRepository;
import com.trisys.Pos.System.service.SuperAdminServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminServices {

    private final StoreRepository storeRepository;

    @Override
    public Map<String, Object> getDashboardSummary() {

        List<Store> stores = storeRepository.findAll();

        long total = stores.size();
        long active = stores.stream().filter(s -> s.getStatus() == StoreStatus.ACTIVE).count();
        long pending = stores.stream().filter(s -> s.getStatus() == StoreStatus.PENDING).count();
        long blocked = stores.stream().filter(s -> s.getStatus() == StoreStatus.BLOCKED).count();

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalStores", total);
        summary.put("activeStores", active);
        summary.put("pendingStores", pending);
        summary.put("blockedStores", blocked);

        return summary;
    }

    /**
     * Store registrations last 7 days
     */
    @Override
    public Map<String, Object> getStoreRegistrationsLast7Days() {

        List<Store> stores = storeRepository.findAll();

        LocalDate today = LocalDate.now();
        Map<String, Integer> registrations = new LinkedHashMap<>();

        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);

            int count = (int) stores.stream()
                    .filter(store ->
                            store.getRegisteredAt().toLocalDate().equals(date))
                    .count();

            registrations.put(date.toString(), count);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("registrations", registrations);

        return result;
    }

    /**
     * Store Status Distribution
     */
    @Override
    public Map<String, Object> getStoreStatusDistribution() {

        List<Store> stores = storeRepository.findAll();

        long active = stores.stream().filter(s -> s.getStatus() == StoreStatus.ACTIVE).count();
        long pending = stores.stream().filter(s -> s.getStatus() == StoreStatus.PENDING).count();
        long blocked = stores.stream().filter(s -> s.getStatus() == StoreStatus.BLOCKED).count();

        Map<String, Object> distribution = new HashMap<>();
        distribution.put("active", active);
        distribution.put("pending", pending);
        distribution.put("blocked", blocked);

        return distribution;
    }
}
