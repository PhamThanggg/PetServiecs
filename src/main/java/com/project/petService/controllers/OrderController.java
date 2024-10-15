package com.project.petService.controllers;

import com.project.petService.dtos.requests.orders.OrderRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.orders.OrderResponse;
import com.project.petService.services.order.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class OrderController {
    OrderService orderService;

    @PostMapping("")
    public ApiResponse<OrderResponse> create(
            @RequestBody @Valid OrderRequest request
            ) throws IOException {
        OrderResponse orderResponses = orderService.createOrder(request);
        return ApiResponse.<OrderResponse>builder()
                .message("Đặt hàng thành công thành công")
                .result(orderResponses)
                .build();
    }

    @GetMapping("")
    public ApiResponse<List<OrderResponse>> getAllOrder(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        List<OrderResponse> orderResponses = orderService
                .getOrderALl(page, limit)
                .getContent();
        int totalCinema = orderResponses.size();
        return ApiResponse.<List<OrderResponse>>builder()
                .message("Tổng số sản phẩm: " + totalCinema)
                .result(orderResponses)
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<OrderResponse>> searchOrder(
            @RequestParam(value = "nameOrder", required = false) String nameOrder,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
//        List<OrderResponse> orderResponse = orderService
//                .searchProductName(nameOrder,  page, limit)
//                .getContent();
//        return ApiResponse.<List<OrderResponse>> builder()
//                .result(orderResponse)
//                .build();
        return null;
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable("id") Long id){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.findById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<OrderResponse> updateOrderById(
            @PathVariable("id") Long id,
            @RequestBody @Valid OrderRequest request
    ){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateOrder(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteOrderById(@PathVariable("id") Long id){
        orderService.deleteOrder(id);
        return ApiResponse.<String>builder()
                .result("Xóa sản phẩm thành công")
                .build();
    }
}
