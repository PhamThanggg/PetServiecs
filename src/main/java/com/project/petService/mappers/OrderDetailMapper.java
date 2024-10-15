package com.project.petService.mappers;


import com.project.petService.dtos.requests.orders.OrderDetailRequest;
import com.project.petService.dtos.requests.orders.OrderRequest;
import com.project.petService.dtos.response.orders.OrderDetailResponse;
import com.project.petService.dtos.response.orders.OrderResponse;
import com.project.petService.entities.Order;
import com.project.petService.entities.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetail toOrderDetail(OrderDetailRequest request);

    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);

    void updateOrderDetail(@MappingTarget OrderDetail orderDetail, OrderDetailRequest request);
}
