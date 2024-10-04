package com.project.petService.services.inventory;

import com.project.petService.dtos.requests.inventory.InventoryRequest;
import com.project.petService.dtos.response.inventory.InventoryResponse;
import com.project.petService.dtos.response.products.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IInventoryService {
    InventoryResponse createInventory(InventoryRequest request);

    InventoryResponse findById(Long id);

    Page<InventoryResponse> getInventoryALl(int page, int limit);

    Page<InventoryResponse> searchProductName(String name, int page, int limit);

    InventoryResponse updateInventory(InventoryRequest request, Long id);

    void deleteInventory(Long id);
}
