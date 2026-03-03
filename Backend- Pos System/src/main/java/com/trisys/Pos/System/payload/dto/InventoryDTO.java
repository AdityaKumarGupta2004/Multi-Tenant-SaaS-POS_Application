package com.trisys.Pos.System.payload.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {

    private Long id;

    private BranchDTO branch;

    private ProductDTO product;

    private Long branchId;

    private Long productId;

    private Integer quantity;

    private LocalDateTime lastUpdate;
}