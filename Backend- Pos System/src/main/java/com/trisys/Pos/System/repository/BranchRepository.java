package com.trisys.Pos.System.repository;

import com.trisys.Pos.System.modal.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    List<Branch> findAByStoreId(Long storeId);

    Branch findBranchByEmail(String email);
}
