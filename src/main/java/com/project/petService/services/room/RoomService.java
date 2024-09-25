package com.project.petService.services.room;

import com.project.petService.dtos.requests.room.RoomRequest;
import com.project.petService.dtos.response.room.RoomResponse;
import com.project.petService.entities.Room;
import com.project.petService.mappers.RoomMapper;
import com.project.petService.repositories.RoomRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomService implements IRoomService {
    RoomRepository roomRepository;
    RoomMapper roomMapper;

    @Override
    public RoomResponse createRoom(RoomRequest request) {
        Room room = roomMapper.toRoom(request);
        return roomMapper.toRoomResponse(roomRepository.save(room));
    }

    @Override
    public List<RoomResponse> getAllRoom() {
        return roomRepository.findAll().stream().map(roomMapper::toRoomResponse).toList();
    }

    @Override
    public RoomResponse updateRoom(RoomRequest request, Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not exists"));

        roomMapper.updateRoom(room, request);
        return roomMapper.toRoomResponse(roomRepository.save(room));
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
