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
    @NotBlank(message = "NAME_NOT_BLANK")
    @Size(min = 1, max = 30, message = "NAME_VALID")
    String fullName;

    @NotBlank(message = "EMAIL_NOT_BLANK")
    @Email(message = "EMAIL_FORMAT")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "EMAIL_FORMAT")
    String email;

    @NotBlank(message = "PHONE_INVALID")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "PHONE_FORMAT")
    String phone;

    @NotBlank(message = "ADDRESS_NOT_BLANK")
    @Size(min = 5, max = 150, message = "ADDRESS_VALID")
    String address;

    @Min(value = 1, message = "QUANTITY_INVALID")
    int quantity;

    @NotBlank(message = "STATUS_NOT_BLANK")
    @Size(min = 3, max = 30, message = "STATUS_INVALID")
    String status;

    Long cartId;

    @NotEmpty(message = "ORDER_DETAIL_NOT_BLANK")
    Set<OrderDetailRequest> orderDetails;
}
