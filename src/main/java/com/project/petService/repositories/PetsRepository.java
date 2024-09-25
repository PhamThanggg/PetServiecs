
package com.project.petService.repositories;

import com.project.petService.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetsRepository extends JpaRepository<Pet, Long> {

}
