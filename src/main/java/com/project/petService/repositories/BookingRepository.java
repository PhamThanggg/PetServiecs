package com.project.petService.repositories;

import com.project.petService.entities.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT p FROM Booking p WHERE (:name IS NULL OR p.name LIKE %:name%)")
    Page<Booking> findByName(String name, Pageable pageable);
}
