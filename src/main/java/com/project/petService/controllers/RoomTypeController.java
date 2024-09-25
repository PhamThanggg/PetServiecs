package com.project.petService.controllers;

import com.project.petService.dtos.requests.room.RoomTypeRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.room.RoomTypeResponse;
import com.project.petService.services.room.RoomTypeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/room_type")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoomTypeController {
    RoomTypeService roomService;

    @PostMapping
    public ApiResponse<RoomTypeResponse> create(@RequestBody @Valid RoomTypeRequest request) {
        return ApiResponse.<RoomTypeResponse>builder()
                .message("Create successfully")
                .result(roomService.createRoom(request)).build();
    }

    @GetMapping
    public ApiResponse<List<RoomTypeResponse>> getAll() {
        return ApiResponse.<List<RoomTypeResponse>>builder()
                .result(roomService.getAllRoom()).build();
    }

    @PutMapping("/{id}")
    public ApiResponse<RoomTypeResponse> updateById(@PathVariable("id") Long id, @RequestBody @Valid RoomTypeRequest request) {
        return ApiResponse.<RoomTypeResponse>builder()
                .result(roomService.updateRoom(request, id)).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteById(@PathVariable("id") Long id) {
        roomService.deleteRoom(id);
        return ApiResponse.<String>builder()
                .result("Room type has been deleted").build();
    }
}
