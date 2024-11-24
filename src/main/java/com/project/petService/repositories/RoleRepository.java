package com.project.petService.repositories;

import com.project.petService.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    boolean existsByName(String name);

    Set<Role> findByIdIn(Set<Long> ids);

    @Query("SELECT p FROM Role p WHERE " +
            "(:name IS NULL OR p.name LIKE %:name%)")
    Page<Role> findRoleName(@Param("name") String name, Pageable pageable);

}
