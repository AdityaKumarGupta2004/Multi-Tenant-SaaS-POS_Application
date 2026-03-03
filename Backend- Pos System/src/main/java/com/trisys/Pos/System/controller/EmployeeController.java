package com.trisys.Pos.System.controller;

import com.trisys.Pos.System.domain.UserRole;
import com.trisys.Pos.System.modal.User;
import com.trisys.Pos.System.payload.dto.UserDTO;
import com.trisys.Pos.System.payload.response.ApiResponse;
import com.trisys.Pos.System.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/store/{storeId}")
    public ResponseEntity<UserDTO> createStoreEmployee(@PathVariable Long storeId,
                                                       @RequestBody UserDTO userDTO) throws Exception {
        UserDTO employee = employeeService.createStoreEmployee(userDTO, storeId);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/branch/{branchId}")
    public ResponseEntity<UserDTO> createBranchEmployee(@PathVariable Long branchId,
                                                        @RequestBody UserDTO userDTO) throws Exception {
        UserDTO employee = employeeService.createBranchEmployee(userDTO, branchId);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateEmployee(@PathVariable Long id,
                                               @RequestBody UserDTO userDTO) throws Exception {
        UserDTO employee = employeeService.updateEmployee(id, userDTO);

        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable Long id) throws Exception {
        employeeService.deleteEmployee(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Employee deleted successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/store/{id}")
    public ResponseEntity<List<UserDTO>> getStoreEmployees(@PathVariable Long id,
                                                           @RequestParam(required = false) UserRole userRole) throws Exception {
        List<UserDTO> employees = employeeService.findStoreEmployees(id, userRole);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/branch/{id}")
    public ResponseEntity<List<UserDTO>> getBranchEmployees(@PathVariable Long id,
                                                            @RequestParam(required = false) UserRole userRole) throws Exception {
        List<UserDTO> employees = employeeService.findBranchEmployees(id, userRole);
        return ResponseEntity.ok(employees);
    }
}