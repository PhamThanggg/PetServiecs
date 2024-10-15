package com.project.petService.services.order;

import com.project.petService.dtos.requests.orders.OrderRequest;
import com.project.petService.dtos.response.orders.OrderResponse;
import com.project.petService.entities.Order;
import com.project.petService.entities.OrderDetail;
import com.project.petService.entities.User;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.OrderMapper;
import com.project.petService.repositories.OrderRepository;
import com.project.petService.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService implements IOrderService {
    OrderRepository orderRepository;
    OrderDetailService orderDetailService;
    UserRepository userRepository;
    OrderMapper orderMapper;

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('MANAGE_ORDER')")
    public OrderResponse createOrder(@RequestBody @Valid OrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

        List<OrderDetail> orderDetails = orderDetailService.createOrderDetail(request.getOrderDetails());
        Order order = orderMapper.toOrder(request);
        order.setUser(user);
        order.setOrderDetails(orderDetails);
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    @PostAuthorize("hasAuthority('MANAGE_ORDER') or returnObject.userId.toString() == authentication.principal.getClaimAsString('id')")
    public OrderResponse findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_EXISTS));

        return orderMapper.toOrderResponse(order);
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGE_ORDER')")
    public Page<OrderResponse> getOrderALl(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));

        return orderRepository.findAll(pageable).map(orderMapper::toOrderResponse);
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGE_ORDER')")
    public Page<OrderResponse> search(String name, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));
//        return orderRepository.findByProductNameContaining(name, pageable).map(orderMapper::toOrderResponse);
        return null;
    }


    @Override
    @PreAuthorize("hasAuthority('MANAGE_ORDER')")
    public OrderResponse updateOrder(@RequestBody @Valid OrderRequest request, Long id) {

        return null;
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGE_ORDER')")
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
