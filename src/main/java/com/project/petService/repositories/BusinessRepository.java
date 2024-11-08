package com.project.petService.repositories;

import com.project.petService.entities.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    boolean existsByName(String name);

    @Query(value = "SELECT s FROM Business s JOIN s.inventory p " +
            "WHERE p.product.id = :productId AND p.quantity >= :requiredQuantity " +
            "ORDER BY (6371 * acos(cos(radians(:latitude)) * cos(radians(s.latitude)) * " +
            "cos(radians(s.longitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(s.latitude)))) ASC")
    List<Business> findNearestStoresWithStock(@Param("productId") Long productId,
                                              @Param("requiredQuantity") int requiredQuantity,
                                              @Param("latitude") double latitude,
                                              @Param("longitude") double longitude);
}
