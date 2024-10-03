package com.project.petService.repositories;

import com.project.petService.entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long id);

    Set<ProductImage> findByIdIn(Set<String> id);

    void deleteByIdIn(Set<Long> id);
}
