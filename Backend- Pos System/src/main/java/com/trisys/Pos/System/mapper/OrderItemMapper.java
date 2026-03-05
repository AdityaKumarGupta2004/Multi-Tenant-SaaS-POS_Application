package com.trisys.Pos.System.mapper;

import com.trisys.Pos.System.modal.OrderItem;
import com.trisys.Pos.System.payload.dto.OrderItemDTO;

public class OrderItemMapper {

    public static OrderItemDTO toDTO(OrderItem orderItem) {
        if (orderItem == null) return null;
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProduct().getId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .product(ProductMapper.toDTO(orderItem.getProduct()))
                .build();
    }
}