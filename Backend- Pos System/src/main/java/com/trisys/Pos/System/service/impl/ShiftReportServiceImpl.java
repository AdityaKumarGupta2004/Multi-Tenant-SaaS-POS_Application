package com.trisys.Pos.System.service.impl;

import com.trisys.Pos.System.domain.PaymentType;
import com.trisys.Pos.System.mapper.ShiftReportMapper;
import com.trisys.Pos.System.modal.*;
import com.trisys.Pos.System.payload.dto.ShiftReportDTO;
import com.trisys.Pos.System.repository.OrderRepository;
import com.trisys.Pos.System.repository.RefundRepository;
import com.trisys.Pos.System.repository.ShiftReportRepository;
import com.trisys.Pos.System.repository.UserRepository;
import com.trisys.Pos.System.service.ShiftReportService;
import com.trisys.Pos.System.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ShiftReportServiceImpl implements ShiftReportService {

    private final ShiftReportRepository shiftReportRepository;
    private final UserService userService;
    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public ShiftReportDTO startShift() throws Exception {
        User currentUser = userService.getCurrentUser();

        LocalDateTime shiftStart = LocalDateTime.now();
        LocalDateTime startOfDay = shiftStart
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
        LocalDateTime endOfDay = shiftStart
                .withHour(23)
                .withMinute(59)
                .withSecond(59);

        Optional<ShiftReport> existing = shiftReportRepository
                .findByCashierAndShiftStartBetween(
                        currentUser,
                        startOfDay,
                        endOfDay
                );

        if (existing.isPresent()) {
            throw new Exception("Shift already started today");
        }

        Branch branch = currentUser.getBranch();
        ShiftReport shiftReport = ShiftReport.builder()
                .cashier(currentUser)
                .shiftStart(shiftStart)
                .branch(branch)
                .build();

        ShiftReport savedReport = shiftReportRepository.save(shiftReport);
        return ShiftReportMapper.toDTO(savedReport);
    }

    @Override
    public ShiftReportDTO endShift(Long shiftReportId,
                                   LocalDateTime shiftEnd) throws Exception {
        User currentUser = userService.getCurrentUser();

        ShiftReport shiftReport = shiftReportRepository
                .findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(currentUser)
                .orElseThrow(() -> new Exception("Shift does not found"));

        shiftReport.setShiftEnd(LocalDateTime.now());

        List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(
                currentUser.getId(),
                shiftReport.getShiftStart(),
                shiftReport.getShiftEnd()
        );
        double totalRefunds = refunds.stream()
                .mapToDouble(refund -> refund.getAmount() != null ?
                        refund.getAmount() : 0.0)
                .sum();

        List<Order> orders = orderRepository.findByCashierAndCreatedAtBetween(
                currentUser,
                shiftReport.getShiftStart(),
                shiftReport.getShiftEnd()
        );
        double totalSales = orders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();
        int totalOrders = orders.size();

        double netSales = totalSales - totalRefunds;

        shiftReport.setTotalRefunds(totalRefunds);
        shiftReport.setTotalSales(totalSales);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setNetSale(netSales);
        shiftReport.setRecentOrders(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders, totalSales));
        shiftReport.setRefunds(refunds);
        shiftReport.setShiftEnd(shiftReport.getShiftEnd());

        ShiftReport savedReport = shiftReportRepository.save(shiftReport);
        return ShiftReportMapper.toDTO(savedReport);
    }

    @Override
    public ShiftReportDTO getShiftReportById(Long shiftReportId) throws Exception {
        return shiftReportRepository.findById(shiftReportId)
                .map(ShiftReportMapper::toDTO)
                .orElseThrow(() -> new Exception("Shift report with id " + shiftReportId + " does not found"));
    }

    @Override
    public List<ShiftReportDTO> getAllShiftReports() {
        List<ShiftReport> shiftReports = shiftReportRepository.findAll();
        return shiftReports.stream()
                .map(ShiftReportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDTO> getShiftReportsByBranchId(Long branchId) {
        List<ShiftReport> shiftReports = shiftReportRepository.findByBranchId(branchId);
        return shiftReports.stream()
                .map(ShiftReportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDTO> getShiftReportsByCashierId(Long cashierId) {
        List<ShiftReport> shiftReports = shiftReportRepository.findByCashierId(cashierId);
        return shiftReports.stream()
                .map(ShiftReportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ShiftReportDTO getCurrentShiftProgress(Long cashierId) throws Exception {
        User currentUser = userService.getCurrentUser();

        ShiftReport shiftReport = shiftReportRepository
                .findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(
                        currentUser
                )
                .orElseThrow(() -> new Exception("No active shift report found for cashier"));

        LocalDateTime now = LocalDateTime.now();
        List<Order> orders = orderRepository.findByCashierAndCreatedAtBetween(
                currentUser,
                shiftReport.getShiftStart(),
                now
        );

        List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(
                currentUser.getId(),
                shiftReport.getShiftStart(),
                now
        );
        double totalRefunds = refunds.stream()
                .mapToDouble(refund -> refund.getAmount() != null ?
                        refund.getAmount() : 0.0)
                .sum();

        double totalSales = orders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();
        int totalOrders = orders.size();
        double netSales = totalSales - totalRefunds;

        shiftReport.setTotalRefunds(totalRefunds);
        shiftReport.setTotalSales(totalSales);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setNetSale(netSales);
        shiftReport.setRecentOrders(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders, totalSales));
        shiftReport.setRefunds(refunds);

        ShiftReport savedReport = shiftReportRepository.save(shiftReport);
        return ShiftReportMapper.toDTO(savedReport);
    }

    @Override
    public ShiftReportDTO getShiftReportByCashierAndDate(Long cashierId,
                                                         LocalDateTime date) throws Exception {
        User cashier = userRepository.findById(cashierId)
                .orElseThrow(() -> new Exception("Cashier with id " + cashierId + " does not found"));

        LocalDateTime start = date.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = date.withHour(23).withMinute(59).withSecond(59);

        ShiftReport report = shiftReportRepository.findByCashierAndShiftStartBetween(
                cashier,
                start,
                end
        ).orElseThrow(() -> new Exception("Shift report does not found for date"));

        return ShiftReportMapper.toDTO(report);
    }

    //-------------------------------------------------------- HELPER METHODS ------------------------------------------
    private List<PaymentSummary> getPaymentSummaries(List<Order> orders, double totalSales) {
        // CASH - order1(amount=1.000), order2(amount=499), order4(amount=999) ✔︎ --> totalAmount = 2.448 --> 2
        // CASH - order2                                ✖︎
        // CARD - order3(amount=3.000)                  ✔︎                        --> totalAmount = 3.000 --> 1
        // CASH - order4                                ✖︎
        // UPI - order5(amount=100), order6(amount=700) ✔︎                        --> totalAmount = 800   --> 3
        //
        // CASH -> 60%  |  CARD -> 10%  |  UPI -> 30%

        Map<PaymentType, List<Order>> grouped = orders.stream()
                .collect(Collectors.groupingBy(order -> order.getPaymentType() != null ?
                        order.getPaymentType() : PaymentType.CASH));

        List<PaymentSummary> paymentSummaries = new ArrayList<>();
        for (Map.Entry<PaymentType, List<Order>> entry : grouped.entrySet()) {
            double amount = entry.getValue().stream()
                    .mapToDouble(Order::getTotalAmount)
                    .sum();

            int transactions = entry.getValue().size();
            double percentage = (amount / totalSales) * 100;

            PaymentSummary paymentSummary = new PaymentSummary();
            paymentSummary.setPaymentType(entry.getKey());
            paymentSummary.setTotalAmount(amount);
            paymentSummary.setTransactionCount(transactions);
            paymentSummary.setPercentage(percentage);
            paymentSummaries.add(paymentSummary);
        }
        return paymentSummaries;
    }

    private List<Product> getTopSellingProducts(List<Order> orders) {
        Map<Product, Integer> productSalesMap = new HashMap<>();

        for (Order order : orders) {
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                productSalesMap.put(
                        product,
                        productSalesMap.getOrDefault(product, 0) + item.getQuantity()
                );
            }
        }
        //  p1 - 5          p5 - 8
        //  p2 - 1          p4 - 7
        //  p3 - 4          p1 - 5
        //  p4 - 7   --->   p3 - 4
        //  p5 - 8          p7 - 3
        //  p6 - 2          p6 - 2
        //  p7 - 3          p2 - 1
        return productSalesMap.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Order> getRecentOrders(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getCreatedAt).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}