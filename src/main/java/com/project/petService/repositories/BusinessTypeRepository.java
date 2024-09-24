package com.project.petService.repositories;

import com.project.petService.entities.BusinessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BusinessTypeRepository extends JpaRepository<BusinessType, Long> {
    Set<BusinessType> findByIdIn(Set<Long> ids);
}
