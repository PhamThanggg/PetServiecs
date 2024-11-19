package com.project.petService.services.order;

import com.project.petService.dtos.requests.orders.OrderDetailRequest;
import com.project.petService.entities.*;
import com.project.petService.mappers.OrderDetailMapper;
import com.project.petService.repositories.AttributeRepository;
import com.project.petService.repositories.InventoryRepository;
import com.project.petService.repositories.OrderDetailRepository;
import com.project.petService.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailService {
    OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> createOrderDetail(Set<OrderDetail> orderDetailUpdate, Order order) {
        Set<OrderDetail> orderDetails = new HashSet<>();

        for (OrderDetail orderDetail : orderDetailUpdate) {
            OrderDetail newOrderDetail = OrderDetail.builder()
                    .quantity(orderDetail.getQuantity())
                    .price(orderDetail.getPrice())
                    .attributeSize(orderDetail.getAttributeSize())
                    .order(order)
                    .build();

            orderDetails.add(newOrderDetail);
        }

        return orderDetailRepository.saveAll(orderDetails);
    }
}
