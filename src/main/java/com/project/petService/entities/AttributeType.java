package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name="attribute_type")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    String name;
}
