package com.trisys.Pos.System.service.impl;

import com.trisys.Pos.System.mapper.InventoryMapper;
import com.trisys.Pos.System.modal.Branch;
import com.trisys.Pos.System.modal.Inventory;
import com.trisys.Pos.System.modal.Product;
import com.trisys.Pos.System.payload.dto.InventoryDTO;
import com.trisys.Pos.System.repository.BranchRepository;
import com.trisys.Pos.System.repository.InventoryRepository;
import com.trisys.Pos.System.repository.ProductRepository;
import com.trisys.Pos.System.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private final BranchRepository branchRepository;

    private final ProductRepository productRepository;

    @Override
    public InventoryDTO createInventory(InventoryDTO inventoryDTO) throws Exception {

        Branch branch = branchRepository.findById(inventoryDTO.getBranchId())
                .orElseThrow(() -> new Exception("Branch not found"));

        Product product = productRepository.findById(inventoryDTO.getProductId())
                .orElseThrow(() -> new Exception("Product not found"));

        Inventory inventory = InventoryMapper.toEntity(inventoryDTO, branch, product);
        Inventory savedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.toDTO(savedInventory);
    }

    @Override
    public InventoryDTO updateInventory(Long inventoryId, InventoryDTO inventoryDTO) throws Exception {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new Exception("Inventory not found"));

        inventory.setQuantity(inventoryDTO.getQuantity());
        Inventory updatedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.toDTO(updatedInventory);
    }

    @Override
    public void deleteInventory(Long inventoryId) throws Exception {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new Exception("Inventory not found"));
        inventoryRepository.delete(inventory);
    }

    @Override
    public InventoryDTO getInventoryById(Long inventoryId) throws Exception {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new Exception("Inventory not found"));
        return InventoryMapper.toDTO(inventory);
    }

    @Override
    public InventoryDTO getInventoryByProductIdAndBranchId(Long productId, Long branchId) {
        Inventory inventory = inventoryRepository.findByProductIdAndBranchId(productId, branchId);
        return InventoryMapper.toDTO(inventory);
    }

    @Override
    public List<InventoryDTO> getAllInventoryByBranchId(Long brandId) {
        List<Inventory> inventories = inventoryRepository.findByBranchId(brandId);
        return inventories.stream().map(InventoryMapper::toDTO).collect(Collectors.toList());
    }
}