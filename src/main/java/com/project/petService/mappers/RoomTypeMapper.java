package com.project.petService.mappers;

import com.project.petService.dtos.requests.room.RoomTypeRequest;
import com.project.petService.dtos.response.room.RoomTypeResponse;
import com.project.petService.entities.RoomType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomTypeMapper {
    RoomType toRoom(RoomTypeRequest request);

    RoomTypeResponse toRoomResponse(RoomType room);

    void updateRoom(@MappingTarget RoomType room, RoomTypeRequest request);
}
