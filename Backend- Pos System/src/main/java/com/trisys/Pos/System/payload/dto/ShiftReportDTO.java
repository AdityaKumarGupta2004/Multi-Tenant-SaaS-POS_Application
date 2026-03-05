package com.trisys.Pos.System.payload.dto;

import com.trisys.Pos.System.modal.PaymentSummary;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShiftReportDTO {

    private Long id;

    private LocalDateTime shiftStart;

    private LocalDateTime shiftEnd;

    private Double totalSales;

    private Double totalRefunds;

    private Double netSale;

    private int totalOrders;

    private UserDTO cashier;

    private Long cashierId;

    private Long branchId;

    private BranchDTO branch;

    private List<PaymentSummary> paymentSummaries;

    private List<ProductDTO> topSellingProducts;

    private List<OrderDTO> recentOrders;

    private List<RefundDTO> refunds;

}