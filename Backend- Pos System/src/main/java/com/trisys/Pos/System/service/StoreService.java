package com.trisys.Pos.System.service;


import com.trisys.Pos.System.domain.StoreStatus;
import com.trisys.Pos.System.exceptions.UserException;
import com.trisys.Pos.System.modal.Store;
import com.trisys.Pos.System.modal.User;
import com.trisys.Pos.System.payload.dto.StoreDTO;

import java.util.List;

public interface StoreService {

    StoreDTO createStore(StoreDTO storeDTO, User user);

    StoreDTO getStoreById(Long storeId) throws Exception;

    List<StoreDTO> getAllStores();

    Store getStoreByAdmin() throws UserException;

    StoreDTO updateStore(Long storeId, StoreDTO storeDTO) throws Exception;

    void deleteStore(Long id) throws UserException;

    StoreDTO getStoreByEmployee() throws UserException;

    StoreDTO moderateStore(Long storeId, StoreStatus status) throws Exception;
}