package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name="invoices")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice {
    @Column(name = "invoiceId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    Double price;

    String paymentMethod;

    LocalDateTime createdDate;

    LocalDateTime paymentDate;

    String status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    Order order;
}

