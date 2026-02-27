package com.trisys.Pos.System.service.impl;

import com.trisys.Pos.System.domain.StoreStatus;
import com.trisys.Pos.System.exceptions.UserException;
import com.trisys.Pos.System.mapper.StoreMapper;
import com.trisys.Pos.System.modal.Store;
import com.trisys.Pos.System.modal.StoreContact;
import com.trisys.Pos.System.modal.User;
import com.trisys.Pos.System.payload.dto.StoreDTO;
import com.trisys.Pos.System.repository.StoreRepository;
import com.trisys.Pos.System.service.StoreService;
import com.trisys.Pos.System.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    private final UserService userService;

    @Override
    public StoreDTO createStore(StoreDTO storeDTO, User user) {
        Store store = StoreMapper.toEntity(storeDTO, user);
        return StoreMapper.toDTO(storeRepository.save(store));
    }

    @Override
    public StoreDTO getStoreById(Long storeId) throws Exception {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new Exception("Store not found...")
        );
        return StoreMapper.toDTO(store);
    }

    @Override
    public List<StoreDTO> getAllStores() {
        return storeRepository.findAll().stream().map(StoreMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Store getStoreByAdmin() throws UserException {
        User admin = userService.getCurrentUser();
        return storeRepository.findByAdmin_Id(admin.getId());
    }

    @Override
    public StoreDTO updateStore(Long id, StoreDTO storeDTO) throws Exception {
        User currentUser = userService.getCurrentUser();
        Store existingStore = storeRepository.findByAdmin_Id(currentUser.getId());

        if (existingStore == null) {
            throw new Exception("Store not found");
        }
        existingStore.setBrand(storeDTO.getBrand());
        existingStore.setDescription(storeDTO.getDescription());

        if (storeDTO.getType() != null) {
            existingStore.setType(storeDTO.getType());
        }

        if (storeDTO.getContact() != null) {
               StoreContact contact = StoreContact
                    .builder()
                    .address(storeDTO.getContact().getAddress())
                    .phone(storeDTO.getContact().getPhone())
                    .email(storeDTO.getContact().getEmail())
                    .build();

            existingStore.setContact(contact);
        }
        Store updatedStore = storeRepository.save(existingStore);

        return StoreMapper.toDTO(updatedStore);
    }

    @Override
    public void deleteStore(Long id) throws UserException {
        Store store = getStoreByAdmin();

        storeRepository.delete(store);
    }

    @Override
    public StoreDTO getStoreByEmployee() throws UserException {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            throw new UserException("You do not have permission to access this store");
        }
        return StoreMapper.toDTO(currentUser.getStore());
    }

    @Override
    public StoreDTO moderateStore(Long id, StoreStatus status) throws Exception {
        Store store = storeRepository.findById(id).orElseThrow(
                () -> new Exception("Store not found...")
        );
        store.setStatus(status);
        Store updatedStore = storeRepository.save(store);

        return StoreMapper.toDTO(updatedStore);
    }
}