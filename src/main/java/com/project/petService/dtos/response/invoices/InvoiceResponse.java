package com.project.petService.dtos.response.invoices;

import com.project.petService.dtos.response.orders.OrderResponse;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceResponse {
    Long id;

    Double price;

    String paymentMethod;

    LocalDateTime createdDate;

    LocalDateTime paymentDate;

    String status;

    OrderResponse order;
}

