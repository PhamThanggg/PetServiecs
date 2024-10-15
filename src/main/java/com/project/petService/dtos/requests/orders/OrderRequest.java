package com.project.petService.dtos.requests.orders;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    @NotBlank(message = "PHONE_INVALID")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "PHONE_FORMAT")
    String phone;

    @NotBlank(message = "ADDRESS_NOT_BLANK")
    @Size(min = 5, max = 30, message = "ADDRESS_VALID")
    String address;

    @Min(value = 1, message = "QUANTITY_INVALID")
    int quantity;

    @NotBlank(message = "STATUS_NOT_BLANK")
    @Size(min = 3, max = 30, message = "STATUS_INVALID")
    String status;

    @NotBlank(message = "STATUS_NOT_BLANK")
    @Size(min = 3, max = 50, message = "STATUS_INVALID")
    String paymentStatus;

    @NotEmpty(message = "ORDER_DETAIL_NOT_BLANK")
    Set<OrderDetailRequest> orderDetails;

    @NotBlank(message = "USER_NOT_NULL")
    String userId;
}
