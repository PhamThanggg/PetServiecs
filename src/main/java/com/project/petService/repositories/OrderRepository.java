package com.project.petService.repositories;

import com.project.petService.entities.Order;
import com.project.petService.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o " +
            "LEFT JOIN o.orderDetails od " +
            "LEFT JOIN od.inventory i " +
            "LEFT JOIN i.product p " +
            "WHERE " +
            "(:name IS NULL OR :name = '' OR p.name LIKE %:name%) AND " +
            "(:status IS NULL OR :status = '' OR o.status = :status) AND " +
            "o.user.id = :userId")
    Page<Order> findOrderOrNameOrStatus(@Param("name") String name,
                                        @Param("status") String status,
                                        @Param("userId") String userId,
                                        Pageable pageable);
}
