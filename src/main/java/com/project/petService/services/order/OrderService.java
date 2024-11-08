package com.project.petService.services.order;

import com.project.petService.dtos.requests.orders.OrderDetailRequest;
import com.project.petService.dtos.requests.orders.OrderRequest;
import com.project.petService.dtos.response.orders.OrderResponse;
import com.project.petService.dtos.response.users.UserResponse;
import com.project.petService.entities.*;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.OrderDetailMapper;
import com.project.petService.mappers.OrderMapper;
import com.project.petService.repositories.OrderRepository;
import com.project.petService.repositories.ProductRepository;
import com.project.petService.repositories.UserRepository;
import com.project.petService.services.business.BusinessService;
import com.project.petService.services.user.UserService;
import com.project.petService.services.util.GeocodingService;
import com.project.petService.services.util.VietnameseStringUtils;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService implements IOrderService {
    OrderRepository orderRepository;
    OrderDetailService orderDetailService;
    OrderMapper orderMapper;
    BusinessService businessService;
    GeocodingService geocodingService;
    UserService userService;
    ProductRepository productRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        User user = userService.getMyUserInfo();

        double[] coordinates = geocodingService.getCoordinates(VietnameseStringUtils.removeDiacritics(request.getAddress()));
        Business business = businessService.findNearestBusinessesForOrder(request, coordinates[0], coordinates[1]);

        // check ton tai product
        Set<Long> ids = request.getOrderDetails().stream().map(OrderDetailRequest::getProductId)
                .collect(Collectors.toSet());
        Set<Product> products = productRepository.findByIdIn(ids);

        Set<Long> existingIds = products.stream()
                .map(Product::getId)
                .collect(Collectors.toSet());

        Set<Long> missingIds = ids.stream()
                .filter(id -> !existingIds.contains(id))
                .collect(Collectors.toSet());

        if (!missingIds.isEmpty()) {
            throw new RuntimeException("The following IDs are not found: " + missingIds);
        }

        Double totalPrice = 0.0;
        for (Product product:products){
            totalPrice += product.getPrice();
        }

        Order order = orderMapper.toOrder(request);
        order.setUser(user);
        order.setBusiness(business);
        order.setTotalPrice(totalPrice);
        Order orderResult = orderRepository.save(order);

        List<OrderDetail> orderDetails = orderDetailService.createOrderDetail(request.getOrderDetails(), ids, business.getId(), orderResult);
        orderResult.setOrderDetails(orderDetails);

        // tạo hóa đơn
        return orderMapper.toOrderResponse(orderResult);
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
    public Page<OrderResponse> getMyOrderALl(int page, int limit, String status, String name) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));
        UserResponse response = userService.getMyInfo();
        return orderRepository.findOrderOrNameOrStatus(name, status, response.getId(), pageable).map(orderMapper::toOrderResponse);
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
