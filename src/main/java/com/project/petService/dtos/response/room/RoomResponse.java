package com.project.petService.dtos.response.room;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomResponse {
    Long id;

    String name;

    int count;

    Long roomTypeId;

    Long businessId;
}
