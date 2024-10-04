package com.project.petService.controllers;

import com.project.petService.dtos.requests.inventory.InventoryRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.inventory.InventoryResponse;
import com.project.petService.services.inventory.InventoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/inventory")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class InventoryController {
    InventoryService inventoryService;

    @PostMapping("")
    public ApiResponse<InventoryResponse> create(
            @RequestBody @Valid InventoryRequest request
            ) throws IOException {
        InventoryResponse inventoryResponses = inventoryService.createInventory(request);
        return ApiResponse.<InventoryResponse>builder()
                .message("Tạo sản phẩm thành công")
                .result(inventoryResponses)
                .build();
    }

    @GetMapping("")
    public ApiResponse<List<InventoryResponse>> getAllInventory(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        List<InventoryResponse> inventoryResponses = inventoryService
                .getInventoryALl(page, limit)
                .getContent();
        int totalCinema = inventoryResponses.size();
        return ApiResponse.<List<InventoryResponse>>builder()
                .message("Tổng số sản phẩm: " + totalCinema)
                .result(inventoryResponses)
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<InventoryResponse>> searchInventory(
            @RequestParam(value = "nameInventory", required = false) String nameInventory,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        List<InventoryResponse> inventoryResponse = inventoryService
                .searchProductName(nameInventory,  page, limit)
                .getContent();
        return ApiResponse.<List<InventoryResponse>> builder()
                .result(inventoryResponse)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<InventoryResponse> getInventoryById(@PathVariable("id") Long id){
        return ApiResponse.<InventoryResponse>builder()
                .result(inventoryService.findById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<InventoryResponse> updateInventoryById(
            @PathVariable("id") Long id,
            @RequestBody @Valid InventoryRequest request
    ){
        return ApiResponse.<InventoryResponse>builder()
                .result(inventoryService.updateInventory(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteInventoryById(@PathVariable("id") Long id){
        inventoryService.deleteInventory(id);
        return ApiResponse.<String>builder()
                .result("Xóa sản phẩm thành công")
                .build();
    }
}
