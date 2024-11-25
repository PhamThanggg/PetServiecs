
package com.project.petService.repositories;

import com.project.petService.entities.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT p FROM Room p WHERE " +
            "(:address IS NULL OR p.business.address LIKE %:address%) AND" +
            "(:name IS NULL OR p.name LIKE %:name%)")
    Page<Room> findByNameAndAddress(@Param("name") String name, @Param("address") String address, Pageable pageable);
}
