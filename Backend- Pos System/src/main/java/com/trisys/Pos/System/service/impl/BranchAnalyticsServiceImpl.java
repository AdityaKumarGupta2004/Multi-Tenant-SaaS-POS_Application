package com.trisys.Pos.System.service.impl;

import com.trisys.Pos.System.modal.Order;
import com.trisys.Pos.System.modal.OrderItem;
import com.trisys.Pos.System.modal.User;
import com.trisys.Pos.System.repository.OrderItemRepository;
import com.trisys.Pos.System.repository.OrderRepository;
import com.trisys.Pos.System.service.BranchAnalyticsServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchAnalyticsServiceImpl implements BranchAnalyticsServices {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private static final Long BRANCH_ID = 1L; // example branch

    /**
     * Daily Sales
     */
    @Override
    public Map<String, Object> getDailySales(int days) {

        Map<String, Object> result = new LinkedHashMap<>();

        LocalDate today = LocalDate.now();

        for (int i = days - 1; i >= 0; i--) {

            LocalDate date = today.minusDays(i);

            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.plusDays(1).atStartOfDay();

            List<Order> orders =
                    orderRepository.findByBranchIdAndCreatedAtBetween(
                            BRANCH_ID, start, end);

            double total = orders.stream()
                    .mapToDouble(Order::getTotalAmount)
                    .sum();

            result.put(date.toString(), total);
        }

        return result;
    }

    /**
     * Top 5 Products
     */
    @Override
    public Map<String, Object> getTopProducts() {

        List<Order> orders =
                orderRepository.findByBranchId(BRANCH_ID);

        Map<String, Integer> productSales = new HashMap<>();
        for (Order order : orders) {
            for (OrderItem item : order.getItems()) {

                String name = item.getProduct().getName();

                productSales.put(
                        name,
                        productSales.getOrDefault(name, 0)
                                + item.getQuantity());
            }
        }
        List<Map<String, Object>> result =
                productSales.entrySet()
                        .stream()
                        .sorted((a, b) -> b.getValue() - a.getValue())
                        .limit(5)
                        .map(e -> {

                            Map<String, Object> map = new HashMap<>();
                            map.put("product", e.getKey());
                            map.put("quantity", e.getValue());

                            return map;

                        })
                        .collect(Collectors.toList());

        return Map.of("topProducts", result);
    }

    /**
     * Top Cashiers
     */
    @Override
    public Map<String, Object> getTopCashiers() {

        List<Order> orders =
                orderRepository.findByBranchId(BRANCH_ID);

        Map<User, Double> cashierSales = new HashMap<>();

        for (Order order : orders) {

            User cashier = order.getCashier();

            cashierSales.put(
                    cashier,
                    cashierSales.getOrDefault(cashier, 0.0)
                            + order.getTotalAmount());
        }

        List<Map<String, Object>> result =
                cashierSales.entrySet()
                        .stream()
                        .sorted((a, b) ->
                                Double.compare(
                                        b.getValue(),
                                        a.getValue()))
                        .limit(5)
                        .map(e -> {

                            Map<String, Object> map = new HashMap<>();
                            map.put("cashier",
                                    e.getKey().getFirstName());
                            map.put("revenue",
                                    e.getValue());

                            return map;

                        })
                        .collect(Collectors.toList());

        return Map.of("topCashiers", result);
    }

    /**
     * Category Sales
     */
    @Override
    public Map<String, Object> getCategorySales(String date) {

        LocalDate localDate = LocalDate.parse(date);

        LocalDateTime start = localDate.atStartOfDay();
        LocalDateTime end = localDate.plusDays(1).atStartOfDay();

        List<Order> orders =
                orderRepository.findByBranchIdAndCreatedAtBetween(
                        BRANCH_ID, start, end);

        Map<String, Double> categorySales = new HashMap<>();

        for (Order order : orders) {

            for (OrderItem item : order.getItems()) {

                String category =
                        item.getProduct()
                                .getCategory()
                                .getName();

                double amount =
                        item.getPrice()
                                * item.getQuantity();

                categorySales.put(
                        category,
                        categorySales.getOrDefault(category, 0.0)
                                + amount);
            }
        }

        return Map.of("categorySales", categorySales);
    }

    /**
     * Today Overview
     */
    @Override
    public Map<String, Object> getTodayOverview() {

        LocalDate today = LocalDate.now();

        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        List<Order> orders =
                orderRepository.findByBranchIdAndCreatedAtBetween(
                        BRANCH_ID, start, end);

        double totalSales =
                orders.stream()
                        .mapToDouble(Order::getTotalAmount)
                        .sum();

        int totalOrders = orders.size();

        long customers =
                orders.stream()
                        .map(o -> o.getCustomer().getId())
                        .distinct()
                        .count();

        Map<String, Object> result = new HashMap<>();

        result.put("sales", totalSales);
        result.put("orders", totalOrders);
        result.put("customers", customers);

        return result;
    }

    /**
     * Payment Breakdown
     */
    @Override
    public Map<String, Object> getPaymentBreakdown(String date) {

        LocalDate localDate = LocalDate.parse(date);

        LocalDateTime start = localDate.atStartOfDay();
        LocalDateTime end = localDate.plusDays(1).atStartOfDay();

        List<Order> orders =
                orderRepository.findByBranchIdAndCreatedAtBetween(
                        BRANCH_ID, start, end);

        Map<String, Double> payment = new HashMap<>();

        for (Order order : orders) {

            String type =
                    order.getPaymentType().name();

            payment.put(
                    type,
                    payment.getOrDefault(type, 0.0)
                            + order.getTotalAmount());
        }

        return Map.of("paymentBreakdown", payment);
    }
}
