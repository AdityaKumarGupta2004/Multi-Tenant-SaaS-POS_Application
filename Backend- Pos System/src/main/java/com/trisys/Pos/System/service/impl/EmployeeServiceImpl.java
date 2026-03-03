package com.trisys.Pos.System.service.impl;

import com.trisys.Pos.System.domain.UserRole;
import com.trisys.Pos.System.mapper.UserMapper;
import com.trisys.Pos.System.modal.Branch;
import com.trisys.Pos.System.modal.Store;
import com.trisys.Pos.System.modal.User;
import com.trisys.Pos.System.payload.dto.UserDTO;
import com.trisys.Pos.System.repository.BranchRepository;
import com.trisys.Pos.System.repository.StoreRepository;
import com.trisys.Pos.System.repository.UserRepository;
import com.trisys.Pos.System.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createStoreEmployee(UserDTO employee, Long storeId) throws Exception {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new Exception("The store with the given id does not found"));
        Branch branch = null;

        if (employee.getRole() == UserRole.ROLE_BRANCH_MANAGER) {
            if (employee.getBranchId() == null) {
                throw new Exception("Branch id is required to create the branch manager");
            }
            branch = branchRepository.findById(employee.getBranchId())
                    .orElseThrow(() -> new Exception("Branch not found"));
        }

        User user = UserMapper.toEntity(employee);
        user.setStore(store);
        user.setBranch(branch);
        user.setPassword(passwordEncoder.encode(employee.getPassword()));
        User savedEmployee = userRepository.save(user);

        if (employee.getRole() == UserRole.ROLE_BRANCH_MANAGER && branch != null) {
            branch.setManager(savedEmployee);
            branchRepository.save(branch);
        }

        return UserMapper.toDTO(savedEmployee);
    }

    @Override
    public UserDTO createBranchEmployee(UserDTO employee, Long branchId) throws Exception {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new Exception("Branch not found"));

        if (employee.getRole() == UserRole.ROLE_BRANCH_CASHIER ||
                employee.getRole() == UserRole.ROLE_BRANCH_MANAGER) {
            User user = UserMapper.toEntity(employee);
            user.setBranch(branch);
            user.setPassword(passwordEncoder.encode(employee.getPassword()));
            return UserMapper.toDTO(userRepository.save(user));
        }

        throw new Exception("Branch role not supported");
    }

    @Override
    public UserDTO updateEmployee(Long employeeId, UserDTO employeeDetails) throws Exception {
        User existingEmployee = userRepository.findById(employeeId)
                .orElseThrow(() -> new Exception("The employee with the given id does not exist"));

        Branch branch = branchRepository.findById(employeeDetails.getBranchId())
                .orElseThrow(() -> new Exception("The branch with the given id does not found"));

        existingEmployee.setEmail(employeeDetails.getEmail());
        existingEmployee.setFirstName(employeeDetails.getFirstName());
        existingEmployee.setLastName(employeeDetails.getLastName());
        existingEmployee.setPassword(employeeDetails.getPassword());
        existingEmployee.setRole(employeeDetails.getRole());
        existingEmployee.setGender(employeeDetails.getGender());
        existingEmployee.setAddress(employeeDetails.getAddress());
        existingEmployee.setDateOfBirth(employeeDetails.getDateOfBirth());
        existingEmployee.setPhoneNumber(employeeDetails.getPhoneNumber());
        existingEmployee.setBranch(branch);

        return UserMapper.toDTO(userRepository.save(existingEmployee));
    }

    @Override
    public void deleteEmployee(Long employeeId) throws Exception {
        User employee = userRepository.findById(employeeId)
                .orElseThrow(() -> new Exception("The employee with the given id does not exist"));

        userRepository.delete(employee);
    }

    @Override
    public List<UserDTO> findStoreEmployees(Long storeId, UserRole role) throws Exception {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new Exception("The store with the given id does not found"));

        return userRepository.findByStore(store)
                .stream()
                .filter(user -> role == null || user.getRole() == role)
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findBranchEmployees(Long branchId, UserRole role) throws Exception {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new Exception("The branch with the given id does not found"));

        return userRepository.findByBranchId(branchId)
                .stream()
                .filter(user -> role == null || user.getRole() == role)
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }
}