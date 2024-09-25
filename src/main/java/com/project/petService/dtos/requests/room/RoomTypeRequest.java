package com.project.petService.dtos.requests.room;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomTypeRequest {
    @NotNull(message = "Tên loại phòng không được để trống")
    String name;
}
