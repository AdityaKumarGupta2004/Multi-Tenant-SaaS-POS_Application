package com.trisys.Pos.System.service;

import com.trisys.Pos.System.domain.OrderStatus;
import com.trisys.Pos.System.domain.PaymentType;
import com.trisys.Pos.System.payload.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    OrderDTO createOrder(OrderDTO order) throws Exception;

    OrderDTO getOrderById(Long orderId) throws Exception;

    List<OrderDTO> getOrdersByBranch(Long brandId,
                                     Long customerId,
                                     Long cashierId,
                                     PaymentType paymentType,
                                     OrderStatus orderStatus) throws Exception;

    List<OrderDTO> getOrderByCashierId(Long cashierId);

    void deleteOrder(Long orderId) throws Exception;

    List<OrderDTO> getTodayOrdersByBranchId(Long branchId) throws Exception;

    List<OrderDTO> getOrdersByCustomerId(Long customerId) throws Exception;

    List<OrderDTO> getTop5RecentOrdersByBranchId(Long branchId) throws Exception;
}