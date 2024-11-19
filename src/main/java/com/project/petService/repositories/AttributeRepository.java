package com.project.petService.repositories;

import com.project.petService.entities.Attribute;
import com.project.petService.entities.Inventory;
import com.project.petService.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    boolean existsByNameAndProductId(String name, Long id);
}
