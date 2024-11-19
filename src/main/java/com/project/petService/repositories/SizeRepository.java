package com.project.petService.repositories;

import com.project.petService.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    boolean existsByName(String name);

    Set<Size> findByIdIn(Set<Long> ids);
}
