package com.project.petService.repositories;

import com.project.petService.entities.AttributeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeTypeRepository extends JpaRepository<AttributeType, Long> {

    boolean existsByName(String name);
}
