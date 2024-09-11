package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name="roomType")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "romeTypeId")
    Long id;

    String name;
}
