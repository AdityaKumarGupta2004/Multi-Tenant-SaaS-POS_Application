package com.trisys.Pos.System.repository;

import com.trisys.Pos.System.modal.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Store findByAdmin_Id(Long id);
}