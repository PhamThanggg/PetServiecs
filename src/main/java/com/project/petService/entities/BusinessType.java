package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name="businessType")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessType extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "businessTypeId")
    Long id;

    String name;

    String description;
}
