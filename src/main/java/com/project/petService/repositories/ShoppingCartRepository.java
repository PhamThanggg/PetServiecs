package com.project.petService.repositories;

import com.project.petService.entities.ShoppingCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    boolean existsByUserIdAndProductId(String userId, Long productId);

    Page<ShoppingCart> findByProductNameContaining(String name, Pageable pageable);
}
