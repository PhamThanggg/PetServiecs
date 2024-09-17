package com.project.petService.repositories;

import com.project.petService.entities.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
    boolean existsByName(String name);
}
