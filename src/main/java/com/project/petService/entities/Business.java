package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name="business")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Business extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "businessId")
    Long id;

    String name;

    String address;

    String phone;

    String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "businessTypeId")
    BusinessType businessType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "areaId")
    Area area;
}
