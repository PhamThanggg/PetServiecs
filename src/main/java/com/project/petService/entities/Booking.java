package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Table(name="booking")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId")
    Long id;

    String status;

    LocalDateTime startTime;

    LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petId")
    Pet pet;

    @OneToMany
    @JoinColumn(name = "serviceId")
    Set<Service> services;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    User user;
}
