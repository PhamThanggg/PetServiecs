package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name="pets")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "petId")
    Long id;

    String name;

    int age;

    Float weight;

    String color;

    @ManyToOne
    @JoinColumn(name = "petTypeId")
    PetType petType;
}
