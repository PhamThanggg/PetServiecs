package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name="petType")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "petTypeId")
    Long Id;

    String name;

    String description;
}
