package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name="rooms")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomId")
    Long id;

    String name;

    int count;

    @ManyToOne
    @JoinColumn(name = "roomTypeId")
    RoomType roomType;

    @ManyToOne
    @JoinColumn(name = "businessId")
    Business business;
}
