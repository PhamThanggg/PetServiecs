package com.project.petService.dtos.requests.booking;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequest {
    @NotNull(message = "Trạng thái không được để trống")
    String status;

    @NotNull(message = "Tên thú cưng không được để trống")
    String namePet;

    @NotNull(message = "Tuổi thú cưng không được để trống")
    String agePet;

    @NotNull(message = "Thời gian không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime startTime;

    @NotNull(message = "Thời gian không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime endTime;

//    @NotNull(message = "Bạn chưa chọn phòng")
//    @Min(value = 1, message = "ID phòng phải >= 1")
//    Long roomId;
//
//    @NotNull(message = "Bạn chưa chọn pet")
//    @Min(value = 1, message = "ID pet phải >= 1")
//    Long petId;

    @NotEmpty(message = "Bạn chưa chọn dịch vụ")
    Set<Long> servicesId;

    @NotNull(message = "Tên người đặt không được để trống")
    String name;

    @NotNull(message = "Số điện thoại không được để trống")
    String phone;
}
