package com.project.petService.services.booking;

import com.project.petService.dtos.requests.booking.BookingRequest;
import com.project.petService.dtos.response.booking.BookingResponse;

import java.util.List;

public interface IBookingService {
    BookingResponse createBooking(BookingRequest request);

    List<BookingResponse> getAllBooking();

    BookingResponse updateBooking(BookingRequest request, Long id);

    void deleteBooking(Long id);
}
