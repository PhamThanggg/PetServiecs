package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name="attribute_size")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    Long price;

    int quantity;

    int quantitySold;

    @ManyToOne
    @JoinColumn(name = "attributeId")
    Attribute attribute;

    @ManyToOne
    @JoinColumn(name = "sizeId")
    Size size;
}
