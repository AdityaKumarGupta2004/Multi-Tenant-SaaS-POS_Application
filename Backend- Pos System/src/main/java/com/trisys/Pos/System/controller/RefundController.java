package com.trisys.Pos.System.controller;

import com.trisys.Pos.System.payload.dto.RefundDTO;
import com.trisys.Pos.System.payload.response.ApiResponse;
import com.trisys.Pos.System.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refunds")
public class RefundController {

    private final RefundService refundService;

    @PostMapping
    public ResponseEntity<RefundDTO> createRefund(@RequestBody RefundDTO refundDTO) throws Exception {
        RefundDTO refund = refundService.createRefund(refundDTO);
        return ResponseEntity.ok(refund);
    }

    @GetMapping
    public ResponseEntity<List<RefundDTO>> getAllRefunds() throws Exception {
        List<RefundDTO> refunds = refundService.getAllRefunds();
        return ResponseEntity.ok(refunds);
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<RefundDTO>> getRefundByCashier(@PathVariable Long cashierId) throws Exception {
        List<RefundDTO> refunds = refundService.getRefundByCashierId(cashierId);
        return ResponseEntity.ok(refunds);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<RefundDTO>> getRefundByBranch(@PathVariable Long branchId) throws Exception {
        List<RefundDTO> refunds = refundService.getRefundsByBranchId(branchId);
        return ResponseEntity.ok(refunds);
    }

    @GetMapping("/shift/{shiftId}")
    public ResponseEntity<List<RefundDTO>> getRefundByShift(@PathVariable Long shiftId) throws Exception {
        List<RefundDTO> refunds = refundService.getRefundByShiftReportId(shiftId);
        return ResponseEntity.ok(refunds);
    }

    @GetMapping("/cashier/{cashierId}/range")
    public ResponseEntity<List<RefundDTO>> getRefundByCashierAndDateRange(
            @PathVariable Long cashierId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime)
            throws Exception {
        List<RefundDTO> refunds = refundService.getRefundsByCashierAndDateRange(
                cashierId,
                startTime,
                endTime
        );
        return ResponseEntity.ok(refunds);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefundDTO> getRefundById(@PathVariable Long id) throws Exception {
        RefundDTO refund = refundService.getRefundById(id);
        return ResponseEntity.ok(refund);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRefund(@PathVariable Long id) throws Exception {
        ApiResponse apiResponse = new ApiResponse();
        refundService.deleteRefundById(id);
        apiResponse.setMessage("Refund successfully deleted");

        return ResponseEntity.ok(apiResponse);
    }
}