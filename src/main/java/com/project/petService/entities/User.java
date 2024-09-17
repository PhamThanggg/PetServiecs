package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Table(name="users")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "userId")
    String id;

    String username;

    String password;

    @Column(length = 10)
    private String phone;

    String email;

    private String image;

    private String fullName;

    private String gender;

    @ManyToMany
    Set<Role> roles;
}