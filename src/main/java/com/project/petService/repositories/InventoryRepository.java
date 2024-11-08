package com.project.petService.repositories;

import com.project.petService.entities.Inventory;
import com.project.petService.entities.OrderDetail;
import com.project.petService.entities.ShoppingCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsByProductIdAndBusinessId(Long productId, Long businessId);

    Page<Inventory> findByProductNameContaining(String name, Pageable pageable);

    Set<Inventory> findByBusinessIdAndProductIdIn(Long businessId, Set<Long> ids);

    @Query("SELECT i.quantity FROM Inventory i WHERE i.business.id = :businessId AND i.product.id = :productId")
    Integer findQuantityByBusinessAndProduct(@Param("businessId") Long businessId, @Param("productId") Long productId);
}
