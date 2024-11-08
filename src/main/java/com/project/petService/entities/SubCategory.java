package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name="sub_category")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subCategoryId")
    Long id;
    String name;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    Category category;
}
