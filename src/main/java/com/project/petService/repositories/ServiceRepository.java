
package com.project.petService.repositories;

import com.project.petService.entities.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Long> {
    Set<Services> findByIdIn(Set<Long> ids);
}
