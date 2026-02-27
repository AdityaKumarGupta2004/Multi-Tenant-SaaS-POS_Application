package com.trisys.Pos.System.payload.dto;

import com.trisys.Pos.System.domain.StoreStatus;
import com.trisys.Pos.System.modal.StoreContact;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreDTO {

    private Long id;

    private String brand;

    private UserDTO admin;

    private LocalDateTime registeredAt;

    private LocalDateTime updatedAt;

    private String description;

    private String type;

    private StoreStatus status;

    private StoreContact contact;
}