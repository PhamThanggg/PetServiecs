package com.project.petService.mappers;


import com.project.petService.dtos.requests.orders.OrderRequest;
import com.project.petService.dtos.response.orders.OrderResponse;
import com.project.petService.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderRequest request);

    @Mapping(source = "user.id", target = "userId")
    OrderResponse toOrderResponse(Order business);

    void updateOrder(@MappingTarget Order business, OrderRequest request);
}
