package com.project.petService.dtos.requests.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.petService.entities.Area;
import com.project.petService.entities.BusinessType;
import com.project.petService.entities.Product;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessRequest {
    @JsonProperty("name")
    @NotNull(message = "Tên kinh doanh không được để trống")
    @Size(min = 2,max = 30, message = "Tên kinh doanh phải từ 2 ký tự trở lên")
    String name;

    @NotNull(message = "Địa chỉ không được để trống")
    @Size(min = 2,max = 60, message = "Địa chỉ phải từ 2 ký tự trở lên")
    String address;


    @Size(min = 10, max = 30, message = "Số điện thoại phải từ 10 ký tự")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Số điện thoại không đúng định dạng")
    String phone;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email không đúng định dạng")
    String email;

    @NotEmpty(message = "Bạn chưa chọn phòng")
    Set<Long> businessTypeId;

    @NotNull(message = "Bạn chưa chọn khu vực")
    @Min(value = 1, message = "ID khu vực phải >= 1")
    Long areaId;

}
