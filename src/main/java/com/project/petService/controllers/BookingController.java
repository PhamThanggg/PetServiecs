package com.project.petService.controllers;

import com.project.petService.dtos.requests.booking.BookingRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.PageResponse;
import com.project.petService.dtos.response.booking.BookingResponse;
import com.project.petService.services.booking.BookingService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/booking")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BookingController {
    BookingService bookingService;

    @PostMapping
    public ApiResponse<BookingResponse> create(@RequestBody @Valid BookingRequest request) {
        return ApiResponse.<BookingResponse>builder()
                .message("Create successfully")
                .result(bookingService.createBooking(request)).build();
    }

    @GetMapping
    public ApiResponse<List<BookingResponse>> getAll() {
        return ApiResponse.<List<BookingResponse>>builder()
                .result(bookingService.getAllBooking()).build();
    }

    @GetMapping("/search")
    public PageResponse<List<BookingResponse>> searchAll(@RequestParam("page") int page,
                                                         @RequestParam(name = "name", required = false) String name,
                                                         @RequestParam("limit") int limit) {
        Page<BookingResponse> bookingResponse = bookingService.getSearchBooking(page, limit, name);
        return PageResponse.<List<BookingResponse>>builder()
                .currentPage(bookingResponse.getNumber())
                .totalPages(bookingResponse.getTotalPages())
                .totalElements(bookingResponse.getTotalElements())
                .pageSize(bookingResponse.getSize())
                .result(bookingResponse.getContent())
                .build();
    }

    @PutMapping
    public ApiResponse<BookingResponse> updateById(@RequestParam("id") Long id, @RequestBody @Valid BookingRequest request) {
        return ApiResponse.<BookingResponse>builder()
                .result(bookingService.updateBooking(request, id)).build();
    }

    @DeleteMapping
    public ApiResponse<String> deleteById(@RequestParam("id") Long id) {
        bookingService.deleteBooking(id);
        return ApiResponse.<String>builder()
                .result("Booking has been deleted").build();
    }
}
