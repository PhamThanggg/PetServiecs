package com.project.petService.repositories;

import com.project.petService.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
//    boolean existsByProductIdAndBusinessId(Long productId, Long businessId);
//
//    Page<Inventory> findByProductNameContaining(String name, Pageable pageable);
}
