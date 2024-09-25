package com.project.petService.services.room;

import com.project.petService.dtos.requests.room.RoomTypeRequest;
import com.project.petService.dtos.response.room.RoomTypeResponse;

import java.util.List;

public interface IRoomTypeService {
    RoomTypeResponse createRoom(RoomTypeRequest request);

    List<RoomTypeResponse> getAllRoom();

    RoomTypeResponse updateRoom(RoomTypeRequest request, Long id);

    void deleteRoom(Long id);
}
