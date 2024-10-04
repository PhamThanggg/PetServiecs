package com.project.petService.repositories;

import com.project.petService.entities.Inventory;
import com.project.petService.entities.ShoppingCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsByProductIdAndBusinessId(Long productId, Long businessId);

    Page<Inventory> findByProductNameContaining(String name, Pageable pageable);
}
