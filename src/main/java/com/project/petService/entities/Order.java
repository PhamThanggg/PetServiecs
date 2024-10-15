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

    String phone;

    String address;

    int quantity;

    Float totalPrice;

    String status;

    String paymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    User user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="orderDetail")
    List<OrderDetail> orderDetails;
}
