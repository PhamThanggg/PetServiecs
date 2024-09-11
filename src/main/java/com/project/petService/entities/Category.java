package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name="category")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    Long id;

    String name;
}
