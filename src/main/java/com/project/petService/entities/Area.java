package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name="areas")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "areaId")
    Long id;

    String name;
}
