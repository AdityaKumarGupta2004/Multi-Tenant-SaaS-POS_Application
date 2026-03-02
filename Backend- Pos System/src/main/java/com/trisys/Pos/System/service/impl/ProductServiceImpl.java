package com.trisys.Pos.System.service.impl;



import com.trisys.Pos.System.mapper.ProductMapper;
import com.trisys.Pos.System.modal.Category;
import com.trisys.Pos.System.modal.Product;
import com.trisys.Pos.System.modal.Store;
import com.trisys.Pos.System.modal.User;
import com.trisys.Pos.System.payload.dto.ProductDTO;
import com.trisys.Pos.System.repository.CategoryRepository;
import com.trisys.Pos.System.repository.ProductRepository;
import com.trisys.Pos.System.repository.StoreRepository;
import com.trisys.Pos.System.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO, User user) throws Exception {
        Store store = storeRepository.findById(productDTO.getStoreId())
                .orElseThrow(() -> new Exception("Store not found"));

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new Exception("Category not found"));

        Product product = ProductMapper.toEntity(productDTO, store, category);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO, User user) throws Exception {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product not found"));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setColor(productDTO.getColor());
        product.setSku(productDTO.getSku());
        product.setImageUrl(productDTO.getImageUrl());
        product.setMrp(productDTO.getMrp());
        product.setSellingPrice(productDTO.getSellingPrice());
        product.setBrand(productDTO.getBrand());
        product.setUpdatedAt(LocalDateTime.now());

        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new Exception("Category not found"));
            product.setCategory(category);
        }

        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDTO(savedProduct);
    }

    @Override
    public void deleteProduct(Long productId, User user) throws Exception {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product not found"));

        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> getAllProducts(User user) {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsByCategoryName(String categoryName, User user) {
        List<Product> products = productRepository.findByCategoryName(categoryName);
        return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsByStoreId(Long storeId) {
        List<Product> products = productRepository.findByStoreId(storeId);
        return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> searchProductsByKeyword(Long storeId, String keyword) {
        List<Product> products = productRepository.searchProductByKeyword(storeId, keyword);
        return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }
}