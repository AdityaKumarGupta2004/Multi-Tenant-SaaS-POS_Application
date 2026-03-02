package com.trisys.Pos.System.service.impl;

import com.trisys.Pos.System.domain.UserRole;
import com.trisys.Pos.System.mapper.CategoryMapper;
import com.trisys.Pos.System.modal.Category;
import com.trisys.Pos.System.modal.Store;
import com.trisys.Pos.System.modal.User;
import com.trisys.Pos.System.payload.dto.CategoryDTO;
import com.trisys.Pos.System.repository.CategoryRepository;
import com.trisys.Pos.System.repository.StoreRepository;
import com.trisys.Pos.System.service.CategoryService;
import com.trisys.Pos.System.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final StoreRepository storeRepository;

    private final UserService userService;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) throws Exception {
        User user = userService.getCurrentUser();

        Store store = storeRepository.findById(categoryDTO.getStoreId())
                .orElseThrow(() -> new Exception("Store not found"));

        Category category = Category.builder()
                .store(store)
                .name(categoryDTO.getName())
                .build();

        checkAuthority(user, category.getStore());
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDTO> getCategoriesByStoreId(Long storeId) {
        List<Category> categories = categoryRepository.findByStoreId(storeId);
        return categories.stream().map(CategoryMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) throws Exception {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Category not exists"));

        User user = userService.getCurrentUser();
        category.setName(categoryDTO.getName());
        checkAuthority(user, category.getStore());
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Category not exists"));

        User user = userService.getCurrentUser();
        checkAuthority(user, category.getStore());
        categoryRepository.delete(category);
    }

    private void checkAuthority(User user, Store store) throws Exception {
        boolean isAdmin = user.getRole().equals(UserRole.ROLE_STORE_ADMIN);
        boolean isManager = user.getRole().equals(UserRole.ROLE_STORE_MANAGER);
        boolean isSameStore = user.equals(store.getAdmin());

        if (!(isAdmin && isSameStore) && !isManager) {
            throw new Exception("You do not have the permission to manage this category");
        }
    }
}