package com.project.petService.services.order;

import com.project.petService.dtos.requests.orders.OrderDetailRequest;
import com.project.petService.dtos.requests.orders.OrderRequest;
import com.project.petService.dtos.response.orders.OrderResponse;
import com.project.petService.dtos.response.users.UserResponse;
import com.project.petService.entities.*;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.OrderMapper;
import com.project.petService.repositories.*;
import com.project.petService.services.user.UserService;
import jakarta.transaction.Transactional;
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

import java.util.HashSet;
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
    UserService userService;
    AttributeSizeRepository attributeSizeRepository;
    ShoppingCartRepository shoppingCartRepository;
    PromotionRepository promotionRepository;
    InvoiceRepository invoiceRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        User user = userService.getMyUserInfo();

        // check giam gia
        Promotion promotion = null;
        if(request.getPromotionName() != null && !request.getPromotionName().trim().isEmpty()){
            promotion = promotionRepository.findValidByName(request.getPromotionName())
                    .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_EXISTS));
        }

        // check ton tai
        Set<Long> cartIds = request.getOrderDetails().stream().map(OrderDetailRequest::getCartId)
                .collect(Collectors.toSet());
        Set<ShoppingCart> shoppingCarts = shoppingCartRepository.findByIdIn(cartIds);

        Set<Long> existingIds = shoppingCarts.stream()
                .map(ShoppingCart::getId)
                .collect(Collectors.toSet());

        Set<Long> missingIds = cartIds.stream()
                .filter(id -> !existingIds.contains(id))
                .collect(Collectors.toSet());

        if (!missingIds.isEmpty()) {
            throw new RuntimeException("The following IDs are not found: " + missingIds);
        }

        Set<Long> attributeSizeIds = shoppingCarts.stream().map(ShoppingCart::getAttributeSizeId)
                .collect(Collectors.toSet());

        Set<AttributeSize> attributeSizes = attributeSizeRepository.findByIdIn(attributeSizeIds);

        // check số lượng
        Set<OrderDetail> orderDetailCreate = new HashSet<>();

        for(ShoppingCart shoppingCart : shoppingCarts){
            AttributeSize attributeSizeNew = null;
            for(AttributeSize attributeSize : attributeSizes){
                if(attributeSize.getId() == shoppingCart.getAttributeSizeId()){
                    attributeSizeNew = attributeSize;
                    if(attributeSize.getQuantity() < shoppingCart.getQuantity()){
                        throw new AppException(ErrorCode.PRODUCT_NOT_ENOUGH);
                    }else{
                        attributeSize.setQuantity(Math.toIntExact(attributeSize.getQuantity() - shoppingCart.getQuantity()));
                        attributeSize.setQuantitySold(Math.toIntExact(attributeSize.getQuantitySold() + shoppingCart.getQuantity()));
                    }
                }
            }
            OrderDetail orderDetail = OrderDetail.builder()
                    .quantity(Math.toIntExact(shoppingCart.getQuantity()))
                    .attributeSize(attributeSizeNew)
                    .price(shoppingCart.getTotalPrice())
                    .build();

            orderDetailCreate.add(orderDetail);
        }

        attributeSizeRepository.saveAll(attributeSizes);

        Double totalPrice = 30000.0;
        for (ShoppingCart shoppingCart : shoppingCarts){
            totalPrice += shoppingCart.getTotalPrice();
        }

        if(promotion != null){
            totalPrice = totalPrice - (totalPrice*(promotion.getDiscount()*1.0/100));
        }

        Order order = orderMapper.toOrder(request);
        order.setUser(user);
        order.setTotalPrice(totalPrice);
        if(promotion != null){
            order.setPromotion(promotion);
        }
        Order orderResult = orderRepository.save(order);

        List<OrderDetail> orderDetails = orderDetailService.createOrderDetail(orderDetailCreate, orderResult);
        orderResult.setOrderDetails(orderDetails);

        // xóa giỏ hàng
        shoppingCartRepository.deleteAllById(cartIds);
        // trừ số lượng khuyến mãi
        if(promotion != null){
            promotion.setCount(promotion.getCount() - 1);
            promotionRepository.save(promotion);
        }

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
    @PreAuthorize("hasAuthority('MANAGE_ORDER') or @securitySecurity.isOrderOwner(#id, authentication)")
    @Transactional
    public OrderResponse updateOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTS));

        if(!order.getStatus().equals("CHO_XAC_NHAN")){
            throw new RuntimeException("Không thể hủy đơn hàng khi người bán đã xác nhận");
        }

        order.setStatus("DA_HUY");
        Order updatedOrder = orderRepository.save(order);

        if(updatedOrder.getStatus().equals("DA_HUY")){
            userService.updateShopStart(order.getUser().getId(), order.getInvoice().getPrice().longValue());
        }

        // cập nhật trạng thái hoàn tiền
        Invoice invoice = invoiceRepository.findByOrderId(order.getId())
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_EXISTS));
        if(invoice.getStatus().equals("DA_THANH_TOAN")){
            invoice.setStatus("DA_HOAN_TIEN");
        }
        invoiceRepository.save(invoice);
        return orderMapper.toOrderResponse(updatedOrder);
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGE_ORDER')")
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
