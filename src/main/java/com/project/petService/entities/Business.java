package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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

    Double latitude;

    Double longitude;

    @OneToMany(mappedBy = "business")
    Set<Inventory> inventory;

    @ManyToMany(fetch = FetchType.LAZY)
    Set<BusinessType> businessType;

    @ManyToOne
    @JoinColumn(name = "areaId")
    Area area;
}
