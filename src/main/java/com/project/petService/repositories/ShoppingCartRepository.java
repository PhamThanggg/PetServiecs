package com.project.petService.repositories;

import com.project.petService.entities.ShoppingCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    boolean existsByUserIdAndProductIdAndAttributeSizeId(String userId, Long productId, Long attributeSizeId);

    ShoppingCart findByUserIdAndProductIdAndAttributeSizeId(String userId, Long productId, Long attributeSizeId);

    @Query("SELECT s FROM ShoppingCart s " +
            "JOIN s.user u " +
            "JOIN s.product p " +
            "WHERE u.id = :userId " +
            "AND (:name IS NULL OR :name = '' OR p.name LIKE %:name%)")
    Page<ShoppingCart> findByUserIdAndProductName(
            @Param("userId") String userId,
            @Param("name") String name,
            Pageable pageable);

    Page<ShoppingCart> findByUserId(String userId, Pageable pageable);

    int countByUserId(String userId);

    Set<ShoppingCart> findByIdIn(Set<Long> ids);
}
