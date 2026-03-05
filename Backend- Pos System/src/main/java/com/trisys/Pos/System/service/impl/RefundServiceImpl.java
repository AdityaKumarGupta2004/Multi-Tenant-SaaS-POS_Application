package com.trisys.Pos.System.service.impl;

import com.trisys.Pos.System.mapper.RefundMapper;
import com.trisys.Pos.System.modal.Branch;
import com.trisys.Pos.System.modal.Order;
import com.trisys.Pos.System.modal.Refund;
import com.trisys.Pos.System.modal.User;
import com.trisys.Pos.System.payload.dto.RefundDTO;
import com.trisys.Pos.System.repository.OrderRepository;
import com.trisys.Pos.System.repository.RefundRepository;
import com.trisys.Pos.System.service.RefundService;
import com.trisys.Pos.System.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final UserService userService;

    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;

    @Override
    public RefundDTO createRefund(RefundDTO refundDTO) throws Exception {
        User cashier = userService.getCurrentUser();
        Order order = orderRepository.findById(refundDTO.getOrderId())
                .orElseThrow(() -> new Exception("Order does not found"));

        Branch branch = order.getBranch();
        Refund createdRefund = Refund.builder()
                .order(order)
                .cashier(cashier)
                .branch(branch)
                .reason(refundDTO.getReason())
                .amount(refundDTO.getAmount())
                .createdAt(refundDTO.getCreatedAt())
                .build();

        Refund savedRefund = refundRepository.save(createdRefund);
        return RefundMapper.toDTO(savedRefund);
    }

    @Override
    public List<RefundDTO> getAllRefunds() throws Exception {
        return refundRepository.findAll().stream()
                .map(RefundMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundByCashierId(Long cashierId) throws Exception {
        return refundRepository.findByCashierId(cashierId).stream()
                .map(RefundMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundByShiftReportId(Long shiftRefundId) throws Exception {
        return refundRepository.findByShiftReportId(shiftRefundId).stream()
                .map(RefundMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundsByCashierAndDateRange(Long cashierId,
                                                           LocalDateTime startDate,
                                                           LocalDateTime endDate) throws Exception {
        return refundRepository.findByCashierIdAndCreatedAtBetween(
                cashierId,
                startDate,
                endDate
        ).stream().map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RefundDTO> getRefundsByBranchId(Long branchId) throws Exception {
        return refundRepository.findByBranchId(branchId).stream()
                .map(RefundMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RefundDTO getRefundById(Long refundId) throws Exception {
        return refundRepository.findById(refundId).map(RefundMapper::toDTO)
                .orElseThrow(() -> new Exception("Refund does not found"));
    }

    @Override
    public void deleteRefundById(Long refundId) throws Exception {
        this.getRefundById(refundId);
        refundRepository.deleteById(refundId);
    }
}