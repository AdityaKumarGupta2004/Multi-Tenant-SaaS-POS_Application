package com.trisys.Pos.System.service;

import com.trisys.Pos.System.payload.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO) throws Exception;

    List<CategoryDTO> getCategoriesByStoreId(Long storeId);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) throws Exception;

    void deleteCategory(Long categoryId) throws Exception;
}
