package com.trisys.Pos.System.service;

import com.trisys.Pos.System.payload.dto.RefundDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface RefundService {

    RefundDTO createRefund(RefundDTO refundDTO) throws Exception;

    List<RefundDTO> getAllRefunds() throws Exception;

    List<RefundDTO> getRefundByCashierId(Long cashierId) throws Exception;

    List<RefundDTO> getRefundByShiftReportId(Long shiftRefundId) throws Exception;

    List<RefundDTO> getRefundsByCashierAndDateRange(Long cashierId,
                                                    LocalDateTime startDate,
                                                    LocalDateTime endDate) throws Exception;

    List<RefundDTO> getRefundsByBranchId(Long branchId) throws Exception;

    RefundDTO getRefundById(Long refundId) throws Exception;

    void deleteRefundById(Long refundId) throws Exception;
}