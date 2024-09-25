package com.project.petService.services.room;

import com.project.petService.dtos.requests.room.RoomRequest;
import com.project.petService.dtos.response.room.RoomResponse;

import java.util.List;

public interface IRoomService {
    RoomResponse createRoom(RoomRequest request);

    List<RoomResponse> getAllRoom();

    RoomResponse updateRoom(RoomRequest request, Long id);

    void deleteRoom(Long id);
}
