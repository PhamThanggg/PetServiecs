package com.project.petService.controllers;

import com.project.petService.dtos.requests.room.RoomRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.room.RoomResponse;
import com.project.petService.services.room.RoomService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/room")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoomController {
    RoomService roomService;

    @PostMapping
    public ApiResponse<RoomResponse> create(@RequestBody @Valid RoomRequest request) {
        return ApiResponse.<RoomResponse>builder()
                .message("Create successfully")
                .result(roomService.createRoom(request)).build();
    }

    @GetMapping
    public ApiResponse<List<RoomResponse>> getAll() {
        return ApiResponse.<List<RoomResponse>>builder()
                .result(roomService.getAllRoom()).build();
    }

    @PutMapping("/{id}")
    public ApiResponse<RoomResponse> updateById(@PathVariable("id") Long id, @RequestBody @Valid RoomRequest request) {
        return ApiResponse.<RoomResponse>builder()
                .result(roomService.updateRoom(request, id)).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteById(@PathVariable("id") Long id) {
        roomService.deleteRoom(id);
        return ApiResponse.<String>builder()
                .result("room has been deleted").build();
    }
}
