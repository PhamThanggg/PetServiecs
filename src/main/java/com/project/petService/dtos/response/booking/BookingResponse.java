package com.project.petService.dtos.response.booking;

import com.project.petService.entities.Pet;
import com.project.petService.entities.Room;
import com.project.petService.entities.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {
    Long id;
    String status;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String namePet;
    String agePet;

    Room room;

    Pet pet;

    Set<Long> servicesId;

    User user;
}
