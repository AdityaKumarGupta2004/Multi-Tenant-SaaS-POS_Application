package com.trisys.Pos.System.controller;


import com.trisys.Pos.System.domain.OrderStatus;
import com.trisys.Pos.System.domain.PaymentType;
import com.trisys.Pos.System.payload.dto.OrderDTO;
import com.trisys.Pos.System.payload.response.ApiResponse;
import com.trisys.Pos.System.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    public final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO order) throws Exception {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByBranch(
            @PathVariable Long branchId,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long cashierId,
            @RequestParam(required = false) PaymentType paymentType,
            @RequestParam(required = false) OrderStatus orderStatus) throws Exception {
        return ResponseEntity.ok(orderService.getOrdersByBranch(
                branchId,
                customerId,
                cashierId,
                paymentType,
                orderStatus)
        );
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<OrderDTO>> getOrderByCashier(@PathVariable Long cashierId) {
        return ResponseEntity.ok(orderService.getOrderByCashierId(cashierId));
    }

    @GetMapping("/today/branch/{branchId}")
    public ResponseEntity<List<OrderDTO>> getTodayOrders(@PathVariable Long branchId) throws Exception {
        return ResponseEntity.ok(orderService.getTodayOrdersByBranchId(branchId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> getCustomerOrders(@PathVariable Long customerId) throws Exception {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }

    @GetMapping("/recent/{branchId}")
    public ResponseEntity<List<OrderDTO>> gerRecentOrders(@PathVariable Long branchId) throws Exception {
        return ResponseEntity.ok(orderService.getTop5RecentOrdersByBranchId(branchId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long id) throws Exception {
        ApiResponse apiResponse = new ApiResponse();
        orderService.deleteOrder(id);
        apiResponse.setMessage("Order successfully deleted");

        return ResponseEntity.ok(apiResponse);
    }
}