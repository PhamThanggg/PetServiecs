package com.project.petService.repositories;

import com.project.petService.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    Set<Product> findByIdIn(Set<Long> ids);
    @Query("SELECT p FROM Product p WHERE " +
            "(:name IS NULL OR :name = '' OR p.name LIKE %:name%) AND " +
            "(:subCategoryId IS NULL OR p.subCategory.id = :subCategoryId) AND " +
            "((:minPrice IS NULL OR p.price >= :minPrice) AND (:maxPrice IS NULL OR p.price <= :maxPrice))")
    Page<Product> findProductOrCategoryOrPrice(@Param("name") String name,
                                 @Param("subCategoryId") Long subCategoryId,
                                 @Param("minPrice") Double minPrice,
                                 @Param("maxPrice")  Double maxPrice,
                                 Pageable pageable);
}
