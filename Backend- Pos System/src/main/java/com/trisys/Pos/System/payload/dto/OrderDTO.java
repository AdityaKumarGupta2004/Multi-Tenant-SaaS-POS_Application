package com.trisys.Pos.System.payload.dto;

import com.trisys.Pos.System.domain.PaymentType;
import com.trisys.Pos.System.modal.Customer;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;

    private Double totalAmount;

    private BranchDTO branch;

    private UserDTO cashier;

    private Customer customer;

    private Long branchId;

    private Long customerId;

    private List<OrderItemDTO> items;

    private PaymentType paymentType;

    private LocalDateTime createdAt;

}
