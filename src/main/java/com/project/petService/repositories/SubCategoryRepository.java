package com.project.petService.repositories;

import com.project.petService.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    boolean existsByNameAndCategoryId(String name, Long categoryId);
}
