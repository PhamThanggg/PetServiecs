package com.project.petService.services.order;

import com.project.petService.dtos.requests.orders.OrderDetailRequest;
import com.project.petService.entities.Inventory;
import com.project.petService.entities.OrderDetail;
import com.project.petService.mappers.OrderDetailMapper;
import com.project.petService.repositories.InventoryRepository;
import com.project.petService.repositories.OrderDetailRepository;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

    public List<OrderDetail> createOrderDetail(@RequestBody @Valid Set<OrderDetailRequest> request) {
        Set<Long> ids = request.stream().map(OrderDetailRequest::getInventoryId)
                .collect(Collectors.toSet());
        Set<Inventory> inventories = inventoryRepository.findByIdIn(ids);

        Set<Long> existingIds = inventories.stream()
                .map(Inventory::getId)
                .collect(Collectors.toSet());

        Set<Long> missingIds = ids.stream()
                .filter(id -> !existingIds.contains(id))
                .collect(Collectors.toSet());

        if (!missingIds.isEmpty()) {
            throw new RuntimeException("The following IDs are not found: " + missingIds);
        }

        Set<OrderDetail> orderDetails = request.stream()
                .map(orderDetailMapper::toOrderDetail)
                .collect(Collectors.toSet());


        orderDetails.forEach(orderDetail -> orderDetail.setInventories(inventories));

        return orderDetailRepository.saveAll(orderDetails);
    }
}
