package com.trisys.Pos.System.service;

import com.trisys.Pos.System.payload.dto.BranchDTO;

import java.util.List;

public interface BranchService {

    BranchDTO createBranch(BranchDTO branchDTO) throws Exception;

    BranchDTO updateBranch(Long branchId, BranchDTO branchDTO) throws Exception;

    void deleteBranch(Long branchId) throws Exception;

    List<BranchDTO> getAllBranchesByStoreId(Long storeId);

    BranchDTO getBranchById(Long branchId) throws Exception;
}