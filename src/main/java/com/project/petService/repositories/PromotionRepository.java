package com.project.petService.repositories;

import com.project.petService.entities.ProductReview;
import com.project.petService.entities.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {
    boolean existsByName(String name);

    Promotion findByName(String name);

    @Query("SELECT pr FROM Promotion pr " +
            "WHERE :name IS NULL OR pr.name LIKE %:name% ")
    Page<Promotion> findByPromotionOrName(
            @Param("name") String name,
            Pageable pageable
    );
}
