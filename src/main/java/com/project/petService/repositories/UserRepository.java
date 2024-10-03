package com.project.petService.repositories;

import com.project.petService.entities.Area;
import com.project.petService.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
