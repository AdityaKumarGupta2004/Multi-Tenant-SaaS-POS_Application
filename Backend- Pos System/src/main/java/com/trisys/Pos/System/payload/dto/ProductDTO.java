package com.trisys.Pos.System.payload.dto;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    private String name;

    private String sku;

    private String description;

    private String color;

    private Double mrp;

    private Double sellingPrice;

    private String brand;

    private String imageUrl;

    private Long storeId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long categoryId;

    private CategoryDTO category;
}