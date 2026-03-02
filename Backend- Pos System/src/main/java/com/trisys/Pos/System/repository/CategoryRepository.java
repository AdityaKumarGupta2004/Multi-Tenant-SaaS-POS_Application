package com.trisys.Pos.System.repository;

import com.trisys.Pos.System.modal.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByStoreId(Long storeId);
}
