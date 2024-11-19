package com.project.petService.repositories;

import com.project.petService.entities.AttributeSize;
import com.project.petService.entities.ShoppingCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    boolean existsByUserIdAndProductIdAndAttributeSizeId(String userId, Long productId, Long attributeSizeId);

    ShoppingCart findByUserIdAndProductIdAndAttributeSizeId(String userId, Long productId, Long attributeSizeId);

    Page<ShoppingCart> findByUserIdAndProductNameContaining(String userId, String name, Pageable pageable);

    Page<ShoppingCart> findByUserId(String userId, Pageable pageable);

    int countByUserId(String userId);

    Set<ShoppingCart> findByIdIn(Set<Long> ids);
}
