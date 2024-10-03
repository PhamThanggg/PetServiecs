package com.project.petService.controllers;

import com.project.petService.dtos.requests.shoppingCart.ShoppingCartRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.shoppingCart.ShoppingCartResponse;
import com.project.petService.services.shoppingCart.ShoppingCartService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/shoppingCart")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class ShoppingCartController {
    ShoppingCartService productService;

    @PostMapping("")
    public ApiResponse<ShoppingCartResponse> create(
            @RequestBody @Valid ShoppingCartRequest request
            ) throws IOException {
        ShoppingCartResponse productResponses = productService.createShoppingCart(request);
        return ApiResponse.<ShoppingCartResponse>builder()
                .message("Tạo sản phẩm thành công")
                .result(productResponses)
                .build();
    }


    @GetMapping("")
    public ApiResponse<List<ShoppingCartResponse>> getAllShoppingCart(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        List<ShoppingCartResponse> productResponses = productService
                .getShoppingCartALl(page, limit)
                .getContent();
        int totalCinema = productResponses.size();
        return ApiResponse.<List<ShoppingCartResponse>>builder()
                .message("Tổng số sản phẩm: " + totalCinema)
                .result(productResponses)
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<ShoppingCartResponse>> searchShoppingCart(
            @RequestParam(value = "name") String name,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        List<ShoppingCartResponse> productResponse = productService
                .searchShoppingByName(name, page, limit)
                .getContent();
        return ApiResponse.<List<ShoppingCartResponse>> builder()
                .result(productResponse)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ShoppingCartResponse> getShoppingCartById(@PathVariable("id") Long id){
        return ApiResponse.<ShoppingCartResponse>builder()
                .result(productService.findById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ShoppingCartResponse> updateShoppingCartById(
            @PathVariable("id") Long id,
            @RequestBody @Valid ShoppingCartRequest request
    ){
        return ApiResponse.<ShoppingCartResponse>builder()
                .result(productService.updateShoppingCart(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteShoppingCartById(@PathVariable("id") Long id){
        productService.deleteShoppingCart(id);
        return ApiResponse.<String>builder()
                .result("Xóa sản phẩm thành công")
                .build();
    }
}
