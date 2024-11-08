package com.project.petService.dtos.requests.invoices;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceUpdateRequest {
    Double price;

    String paymentMethod;

    String status;

    LocalDateTime paymentDate;
}

