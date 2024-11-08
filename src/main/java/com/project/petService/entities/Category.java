package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    Set<SubCategory> subCategory;
}
