package com.project.petService.entities;


import jakarta.persistence.*;
import lombok.*;

@Table(name="inventory")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventoryId")
    Long id;

    @ManyToOne
    @JoinColumn(name = "businessId")
    Business business;

    @ManyToOne
    @JoinColumn(name = "productId")
    Product product;

    int quantity;

    int quantitySold;

}
