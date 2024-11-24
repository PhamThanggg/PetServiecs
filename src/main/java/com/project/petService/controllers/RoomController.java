package com.project.petService.controllers;

import com.project.petService.dtos.requests.room.RoomRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.PageResponse;
import com.project.petService.dtos.response.room.RoomResponse;
import com.project.petService.services.room.RoomService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
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

    @GetMapping("/search")
    public PageResponse<List<RoomResponse>> searchAll(@RequestParam("page") int page,
                                                      @RequestParam(name = "name", required = false) String name,
                                                      @RequestParam(name = "address", required = false) String address,
                                                      @RequestParam("limit") int limit) {
        Page<RoomResponse> roomResponse = roomService.getSearchRoom(page, limit, name, address);
        return PageResponse.<List<RoomResponse>>builder()
                .currentPage(roomResponse.getNumber())
                .totalPages(roomResponse.getTotalPages())
                .totalElements(roomResponse.getTotalElements())
                .pageSize(roomResponse.getSize())
                .result(roomResponse.getContent())
                .build();
    }

    @GetMapping("/get")
    public ApiResponse<RoomResponse> getRoom(@RequestParam Long id) {
        return ApiResponse.<RoomResponse>builder()
                .result(roomService.getRoom(id)).build();
    }

    @PutMapping()
    public ApiResponse<RoomResponse> updateById(@RequestParam("id") Long id, @RequestBody @Valid RoomRequest request) {
        return ApiResponse.<RoomResponse>builder()
                .result(roomService.updateRoom(request, id)).build();
    }

    @DeleteMapping()
    public ApiResponse<String> deleteById(@RequestParam("id") Long id) {
        roomService.deleteRoom(id);
        return ApiResponse.<String>builder()
                .result("room has been deleted").build();
    }
}
