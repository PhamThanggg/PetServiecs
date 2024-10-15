package com.project.petService.dtos.response.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    Long id;

    String phone;

    String address;

    int quantity;

    Float totalPrice;

    String status;

    String paymentStatus;

    String userId;

    List<OrderDetailResponse> orderDetails;
}
