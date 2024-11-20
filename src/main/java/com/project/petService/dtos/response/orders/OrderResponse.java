package com.project.petService.dtos.response.orders;

import com.project.petService.dtos.response.invoices.InvoiceResponse;
import com.project.petService.dtos.response.promotion.PromotionResponse;
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

    String fullName;

    String email;

    String phone;

    String address;

    int quantity;

    Double totalPrice;

    String status;

    String userId;

    List<OrderDetailResponse> orderDetails;

    InvoiceResponse invoice;

    PromotionResponse promotionResponse;
}
