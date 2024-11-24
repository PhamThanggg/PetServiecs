package com.project.petService.services.room;

import com.project.petService.dtos.requests.room.RoomRequest;
import com.project.petService.dtos.response.room.RoomResponse;
import com.project.petService.entities.Business;
import com.project.petService.entities.Room;
import com.project.petService.entities.RoomType;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.RoomMapper;
import com.project.petService.repositories.BusinessRepository;
import com.project.petService.repositories.RoomRepository;
import com.project.petService.repositories.RoomTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomService implements IRoomService {
    RoomRepository roomRepository;
    RoomMapper roomMapper;
    BusinessRepository businessRepository;
    RoomTypeRepository roomTypeRepository;

    @Override
    @PreAuthorize("hasAuthority('MANAGE_HOTEL')")
    public RoomResponse createRoom(RoomRequest request) {
        Room room = roomMapper.toRoom(request);
        Business business = businessRepository.findById(request.getBusinessId())
                .orElseThrow(() -> new AppException(ErrorCode.BUSINESS_NOT_EXISTS));
        RoomType roomType = roomTypeRepository.findById(request.getRoomTypeId())
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_TYPE_NOT_EXITS));
        room.setRoomType(roomType);
        room.setBusiness(business);
        return roomMapper.toRoomResponse(roomRepository.save(room));
    }

    @Override
    public List<RoomResponse> getAllRoom() {
        return roomRepository.findAll().stream().map(roomMapper::toRoomResponse).toList();
    }

    public Page<RoomResponse> getSearchRoom(int page, int limit, String name, String address) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));

        return roomRepository.findByNameAndAddress(name, address, pageable).map(roomMapper::toRoomResponse);
    }

    public RoomResponse getRoom(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXITS));
        return roomMapper.toRoomResponse(room);
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGE_HOTEL')")
    public RoomResponse updateRoom(RoomRequest request, Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not exists"));
        Business business = businessRepository.findById(request.getBusinessId())
                .orElseThrow(() -> new AppException(ErrorCode.BUSINESS_NOT_EXISTS));
        RoomType roomType = roomTypeRepository.findById(request.getRoomTypeId())
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_TYPE_NOT_EXITS));
        room.setRoomType(roomType);
        room.setBusiness(business);

        roomMapper.updateRoom(room, request);
        return roomMapper.toRoomResponse(roomRepository.save(room));
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGE_HOTEL')")
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
