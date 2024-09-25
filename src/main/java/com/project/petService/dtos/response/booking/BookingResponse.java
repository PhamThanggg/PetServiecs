package com.project.petService.dtos.response.booking;

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
    String roomId;

    String petId;

    Set<Long> servicesId;

    String userId;
}
