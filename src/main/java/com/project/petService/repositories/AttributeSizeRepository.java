package com.project.petService.repositories;

import com.project.petService.entities.AttributeSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AttributeSizeRepository extends JpaRepository<AttributeSize, Long> {

    @Query("SELECT s FROM AttributeSize s " +
            "JOIN s.attribute a " +
            "JOIN a.product p " +
            "WHERE s.id = :id AND p.id = :productId")
    Optional<AttributeSize> findBySizeWithProductId(Long id, Long productId);

    Set<AttributeSize> findByIdIn(Set<Long> ids);

    boolean existsByAttributeIdAndSizeId(Long attributeId, Long sizeId);
}
