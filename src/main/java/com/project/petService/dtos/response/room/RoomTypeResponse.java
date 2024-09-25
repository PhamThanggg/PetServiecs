package com.project.petService.dtos.response.room;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomTypeResponse {
    Long id;

    String name;
}
