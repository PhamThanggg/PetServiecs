package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Table(name="orderDetail")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderDetailId")
    Long id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    Set<Inventory> inventories;

    int quantity;

    Double price;
}
