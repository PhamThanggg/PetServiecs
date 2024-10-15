package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Table(name="products")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    Long id;

    String name;

    String description;

    Double price;

    @Builder.Default
    Float star = 0.0f;

    String brand;

    String status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    Set<ProductImage> productImages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    Category category;

}
