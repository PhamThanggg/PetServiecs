
package com.project.petService.repositories;

import com.project.petService.entities.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PetsRepository extends JpaRepository<Pet, Long> {
    @Query("SELECT p FROM Pet p WHERE " +
            "(:type IS NULL OR p.petType.id = :type) AND" +
            "(:name IS NULL OR p.name LIKE %:name%)")
    Page<Pet> findByNameAndType(@Param("name") String name, Long type, Pageable pageable);
}
