
package com.project.petService.repositories;

import com.project.petService.entities.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Long> {
    Set<Services> findByIdIn(Set<Long> ids);

    @Query("SELECT p FROM Services p WHERE " +
            "(:name IS NULL OR p.name LIKE %:name%)")
    Page<Services> findByName(@Param("name") String name, Pageable pageable);
}
