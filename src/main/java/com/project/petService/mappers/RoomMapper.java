package com.project.petService.mappers;

import com.project.petService.dtos.requests.room.RoomRequest;
import com.project.petService.dtos.response.room.RoomResponse;
import com.project.petService.entities.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    Room toRoom(RoomRequest request);

    @Mapping(source = "roomType.id", target = "roomTypeId")
    @Mapping(source = "business.id", target = "businessId")
    RoomResponse toRoomResponse(Room room);

    void updateRoom(@MappingTarget Room room, RoomRequest request);
}
