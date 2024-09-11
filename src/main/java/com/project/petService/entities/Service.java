package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name="services")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serviceId")
    Long id;

    String name;
}
