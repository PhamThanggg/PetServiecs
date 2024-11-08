package com.project.petService.services.order;

import com.project.petService.dtos.requests.orders.OrderDetailRequest;
import com.project.petService.entities.Inventory;
import com.project.petService.entities.Order;
import com.project.petService.entities.OrderDetail;
import com.project.petService.entities.Product;
import com.project.petService.mappers.OrderDetailMapper;
import com.project.petService.repositories.InventoryRepository;
import com.project.petService.repositories.OrderDetailRepository;
import com.project.petService.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailService {
    InventoryRepository inventoryRepository;
    OrderDetailRepository orderDetailRepository;
    OrderDetailMapper orderDetailMapper;


    public List<OrderDetail> createOrderDetail(Set<OrderDetailRequest> request, Set<Long> ids, Long businessId, Order order) {
        Set<OrderDetail> orderDetails = request.stream()
                .map(orderDetailMapper::toOrderDetail)
                .collect(Collectors.toSet());

        Set<Inventory> inventories = inventoryRepository.findByBusinessIdAndProductIdIn(businessId, ids);
        Iterator<Inventory> inventoryIterator = inventories.iterator();
        orderDetails.forEach(orderDetail -> {
            if (inventoryIterator.hasNext()) {
                orderDetail.setInventory(inventoryIterator.next());
            }
            orderDetail.setOrder(order);
        });

        return orderDetailRepository.saveAll(orderDetails);
    }
}
