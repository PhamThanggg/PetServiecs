package com.project.petService.services.order;

import com.project.petService.dtos.requests.orders.OrderRequest;
import com.project.petService.dtos.response.orders.OrderResponse;
import org.springframework.data.domain.Page;

public interface IOrderService {
    OrderResponse createOrder(OrderRequest request);

    OrderResponse findById(Long id);

    Page<OrderResponse> getOrderALl(int page, int limit);

    Page<OrderResponse> getMyOrderALl(int page, int limit, String status, String name);

    Page<OrderResponse> search(String name, int page, int limit);

    OrderResponse updateOrder(OrderRequest request, Long id);

    void deleteOrder(Long id);
}
