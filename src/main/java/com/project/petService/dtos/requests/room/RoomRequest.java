package com.project.petService.dtos.requests.room;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomRequest {
    @NotNull(message = "Tên phòng không được để trống")
    String name;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải >= 1")
    int count;

    @NotNull(message = "Loại phòng không được để trống")
    @Min(value = 1, message = "ID loại phòng phải >= 1")
    Long roomTypeId;

    @NotNull(message = "Business không được để trống")
    @Min(value = 1, message = "ID phải >= 1")
    Long businessId;
}
