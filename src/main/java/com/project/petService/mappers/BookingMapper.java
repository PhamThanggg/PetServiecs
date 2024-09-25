package com.project.petService.mappers;

import com.project.petService.dtos.requests.booking.BookingRequest;
import com.project.petService.dtos.response.booking.BookingResponse;
import com.project.petService.entities.Booking;
import com.project.petService.entities.Services;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    Booking toBooking(BookingRequest request);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "pet.id", target = "petId")
    @Mapping(target = "servicesId", expression = "java(mapServiceTypeIds(booking.getServices()))")
    BookingResponse toBookingResponse(Booking booking);

    void updateBooking(@MappingTarget Booking booking, BookingRequest request);

    default Set<Long> mapServiceTypeIds(Set<Services> services) {
        return services.stream()
                .map(Services::getId)
                .collect(Collectors.toSet());
    }
}
