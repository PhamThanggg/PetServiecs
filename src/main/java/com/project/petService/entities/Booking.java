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
public class Booking extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId")
    Long id;

    String status;

    LocalDateTime startTime;

    LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "roomId")
    Room room;

    @ManyToOne
    @JoinColumn(name = "petId")
    Pet pet;

    @ManyToMany
    Set<Services> services;

    @ManyToOne
    @JoinColumn(name = "userId")
    User user;
}
