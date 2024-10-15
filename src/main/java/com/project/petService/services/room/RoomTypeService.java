package com.project.petService.services.room;

import com.project.petService.dtos.requests.room.RoomTypeRequest;
import com.project.petService.dtos.response.room.RoomTypeResponse;
import com.project.petService.entities.RoomType;
import com.project.petService.mappers.RoomTypeMapper;
import com.project.petService.repositories.RoomTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomTypeService implements IRoomTypeService {
    RoomTypeRepository roomRepository;
    RoomTypeMapper roomMapper;

    @Override
    @PreAuthorize("hasAuthority('MANAGE_HOTEL')")
    public RoomTypeResponse createRoom(RoomTypeRequest request) {
        RoomType room = roomMapper.toRoom(request);
        return roomMapper.toRoomResponse(roomRepository.save(room));
    }

    @Override
    public List<RoomTypeResponse> getAllRoom() {
        return roomRepository.findAll().stream().map(roomMapper::toRoomResponse).toList();
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGE_HOTEL')")
    public RoomTypeResponse updateRoom(RoomTypeRequest request, Long id) {
        RoomType room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room type not exists"));

        roomMapper.updateRoom(room, request);
        return roomMapper.toRoomResponse(roomRepository.save(room));
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
