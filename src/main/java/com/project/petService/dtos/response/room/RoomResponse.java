package com.project.petService.dtos.response.room;

import com.project.petService.entities.Business;
import com.project.petService.entities.RoomType;
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

    RoomType roomType;

    Business business;
}
