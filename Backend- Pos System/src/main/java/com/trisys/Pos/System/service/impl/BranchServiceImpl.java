package com.trisys.Pos.System.service.impl;

import com.trisys.Pos.System.mapper.BranchMapper;
import com.trisys.Pos.System.modal.Branch;
import com.trisys.Pos.System.modal.Store;
import com.trisys.Pos.System.modal.User;
import com.trisys.Pos.System.payload.dto.BranchDTO;
import com.trisys.Pos.System.repository.BranchRepository;
import com.trisys.Pos.System.repository.StoreRepository;
import com.trisys.Pos.System.service.BranchService;
import com.trisys.Pos.System.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    private final StoreRepository storeRepository;

    private final UserService userService;

    @Override
    public BranchDTO createBranch(BranchDTO branchDTO) throws Exception {
        User currentUser = userService.getCurrentUser();
        Store store = storeRepository.findByAdmin_Id(currentUser.getId());

        Branch toCheck = branchRepository.findBranchByEmail(branchDTO.getEmail());

        if (toCheck != null) {
            throw new Exception("Email already exists");
        }

        Branch branch = BranchMapper.toEntity(branchDTO, store);
        Branch savedBranch = branchRepository.save(branch);
        return BranchMapper.toDTO(savedBranch);
    }

    @Override
    public BranchDTO updateBranch(Long branchId, BranchDTO branchDTO) throws Exception {
        Branch existingBranch = branchRepository.findById(branchId)
                .orElseThrow(() -> new Exception("Branch does not exists"));

        existingBranch.setName(branchDTO.getName());
        existingBranch.setWorkingDays(branchDTO.getWorkingDays());
        existingBranch.setEmail(branchDTO.getEmail());
        existingBranch.setPhone(branchDTO.getPhone());
        existingBranch.setAddress(branchDTO.getAddress());
        existingBranch.setOpeningTime(branchDTO.getOpeningTime());
        existingBranch.setClosingTime(branchDTO.getClosingTime());
        existingBranch.setUpdatedAt(LocalDateTime.now());

        Branch updatedBranch = branchRepository.save(existingBranch);
        return BranchMapper.toDTO(updatedBranch);
    }

    @Override
    public void deleteBranch(Long branchId) throws Exception {
        Branch existingBranch = branchRepository.findById(branchId)
                .orElseThrow(() -> new Exception("Branch does not exists"));

        branchRepository.delete(existingBranch);
    }

    @Override
    public List<BranchDTO> getAllBranchesByStoreId(Long storeId) {
        List<Branch> branches = branchRepository.findAByStoreId(storeId);
        return branches.stream().map(BranchMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public BranchDTO getBranchById(Long branchId) throws Exception {
        Branch existingBranch = branchRepository.findById(branchId)
                .orElseThrow(() -> new Exception("Branch does not exists"));

        return BranchMapper.toDTO(existingBranch);
    }
}