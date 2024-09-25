
package com.project.petService.repositories;

import com.project.petService.entities.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Long> {
    boolean existsByName(String name);
}
