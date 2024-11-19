package com.project.petService.dtos.requests.products;

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
public class ProductRequest {
    @NotBlank(message = "NAME_NOT_BLANK")
    @Size(min = 2,max = 30, message = "NAME_VALID")
    String name;

    @Size(min = 2, max = 1000, message = " DESCRIPTION_INVALID")
    String description;

    @NotNull(message = "PRICE_NOT_NULL")
    @Min(value = 0, message = "PRICE_INVALID")
    Double price;


    @NotBlank(message = "BRAND_NOT_BLANK")
    @Size(min = 2, max = 60, message = "BRAND_INVALID")
    String brand;

    @NotBlank(message = "STATUS_NOT_BLANK")
    @Size(min = 2, max = 20, message = "STATUS_INVALID")
    String status;

    int percentDiscount;

    @NotNull(message = "CATEGORY_NOT_BLANK")
    @Min(value = 1, message = "CATEGORY_ID_INVALID")
    Long subCategoryId;
}
