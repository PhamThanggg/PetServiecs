package com.project.petService.services.inventory;

import com.project.petService.dtos.requests.inventory.InventoryRequest;
import com.project.petService.dtos.response.inventory.InventoryResponse;
import com.project.petService.entities.Business;
import com.project.petService.entities.Inventory;
import com.project.petService.entities.Product;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.InventoryMapper;
import com.project.petService.repositories.BusinessRepository;
import com.project.petService.repositories.InventoryRepository;
import com.project.petService.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryService implements IInventoryService {
    InventoryRepository inventoryRepository;
    BusinessRepository businessRepository;
    ProductRepository productRepository;
    InventoryMapper inventoryMapper;

    @Override
    @PreAuthorize("hasAuthority('MANAGE_INVENTORY')")
    public InventoryResponse createInventory(@RequestBody @Valid InventoryRequest request) {
        if(inventoryRepository.existsByProductIdAndBusinessId(request.getProductId(), request.getBusinessId())){
            throw new AppException(ErrorCode.INVENTORY_EXISTS);
        }

        Business business = businessRepository.findById(request.getBusinessId())
                .orElseThrow(() -> new AppException(ErrorCode.BUSINESS_NOT_EXISTS));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS));


        Inventory inventory = inventoryMapper.toInventory(request);
        inventory.setBusiness(business);
        inventory.setProduct(product);
        return inventoryMapper.toInventoryResponse(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponse findById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_EXISTS));

        return inventoryMapper.toInventoryResponse(inventory);
    }

    @Override
    public Page<InventoryResponse> getInventoryALl(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));

        return inventoryRepository.findAll(pageable).map(inventoryMapper::toInventoryResponse);
    }

    @Override
    public Page<InventoryResponse> searchProductName(String name, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));
        return inventoryRepository.findByProductNameContaining(name, pageable).map(inventoryMapper::toInventoryResponse);
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGE_INVENTORY')")
    public InventoryResponse updateInventory(@RequestBody @Valid InventoryRequest request, Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BUSINESS_NAME_NOT_EXISTS));

        Business business = businessRepository.findById(request.getBusinessId())
                .orElseThrow(() -> new AppException(ErrorCode.BUSINESS_NOT_EXISTS));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS));

        if(inventory.getBusiness().getId() != request.getBusinessId() || inventory.getProduct().getId() != request.getProductId()){
            if(inventoryRepository.existsByProductIdAndBusinessId(request.getProductId(), request.getBusinessId())){
                throw new AppException(ErrorCode.INVENTORY_EXISTS);
            }
        }
        inventoryMapper.updateInventory(inventory, request);
        inventory.setProduct(product);
        inventory.setBusiness(business);
        return inventoryMapper.toInventoryResponse(inventory);
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGE_INVENTORY')")
    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }
}
