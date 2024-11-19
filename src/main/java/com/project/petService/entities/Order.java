package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Table(name="orders")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    Long id;

    String fullName;

    String email;

    String phone;

    String address;

    int quantity;

    Double totalPrice;

    String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    List<OrderDetail> orderDetails;

    @OneToOne(mappedBy = "order")
    Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotionId")
    Promotion promotion;
}
