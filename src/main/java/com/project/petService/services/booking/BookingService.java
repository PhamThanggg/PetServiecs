package com.project.petService.services.booking;

import com.project.petService.dtos.requests.booking.BookingRequest;
import com.project.petService.dtos.response.booking.BookingResponse;
import com.project.petService.entities.Booking;
import com.project.petService.entities.Room;
import com.project.petService.entities.Services;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.BookingMapper;
import com.project.petService.repositories.BookingRepository;
import com.project.petService.repositories.RoomRepository;
import com.project.petService.repositories.ServiceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingService implements IBookingService {
    BookingRepository bookingRepository;
    ServiceRepository serviceRepository;
    RoomRepository roomRepository;
    BookingMapper bookingMapper;

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXITS));

        Set<Services> liServices = serviceRepository.findByIdIn(request.getServicesId());
        Set<Long> foundIds = liServices.stream().map(Services::getId).collect(Collectors.toSet());
        List<Long> missingGenreId = request.getServicesId().stream()
                .filter(id -> !foundIds.contains(id)).toList();
        if(!missingGenreId.isEmpty()){
            String errorMessage = missingGenreId.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            throw new RuntimeException("Dịch vụ doanh với id = " + errorMessage + " không tồn tại!");
        }

        Booking booking = bookingMapper.toBooking(request);
        booking.setServices(liServices);
        booking.setRoom(room);

        return bookingMapper.toBookingResponse(bookingRepository.save(booking));
    }

    @Override
    public List<BookingResponse> getAllBooking() {
        return bookingRepository.findAll().stream().map(bookingMapper::toBookingResponse).toList();
    }

    @Override
    public BookingResponse updateBooking(BookingRequest request, Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not exists"));

        bookingMapper.updateBooking(booking, request);
        return bookingMapper.toBookingResponse(bookingRepository.save(booking));
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
