package com.project.petService.mappers;


import com.project.petService.dtos.requests.inventory.InventoryRequest;
import com.project.petService.dtos.response.inventory.InventoryResponse;
import com.project.petService.entities.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    Inventory toInventory(InventoryRequest request);

    @Mapping(source = "business.id", target = "businessId")
    @Mapping(source = "product.id", target = "productId")
    InventoryResponse toInventoryResponse(Inventory product);

    void updateInventory(@MappingTarget Inventory inventory, InventoryRequest request);
}
