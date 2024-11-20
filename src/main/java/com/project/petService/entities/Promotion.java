package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name="promotion")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    String id;

    String name;

    String description;

    int discount;

    int count;

    LocalDate startDate;

    LocalDate endDate;
}
