package com.trisys.Pos.System.mapper;

import com.trisys.Pos.System.modal.Store;
import com.trisys.Pos.System.modal.User;
import com.trisys.Pos.System.payload.dto.StoreDTO;

public class StoreMapper {

    public static StoreDTO toDTO(Store store) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setId(store.getId());
        storeDTO.setBrand(store.getBrand());
        storeDTO.setAdmin(UserMapper.toDTO(store.getAdmin()));
        storeDTO.setRegisteredAt(store.getRegisteredAt());
        storeDTO.setUpdatedAt(store.getUpdatedAt());
        storeDTO.setDescription(store.getDescription());
        storeDTO.setType(store.getType());
        storeDTO.setStatus(store.getStatus());
        storeDTO.setContact(store.getContact());

        return storeDTO;
    }

    public static Store toEntity(StoreDTO storeDTO, User storeAdmin) {
        Store store = new Store();
        store.setId(storeDTO.getId());
        store.setBrand(storeDTO.getBrand());
        store.setAdmin(storeAdmin);
        store.setRegisteredAt(storeDTO.getRegisteredAt());
        store.setUpdatedAt(storeDTO.getUpdatedAt());
        store.setDescription(storeDTO.getDescription());
        store.setType(storeDTO.getType());
        store.setStatus(storeDTO.getStatus());
        store.setContact(storeDTO.getContact());

        return store;
    }
}