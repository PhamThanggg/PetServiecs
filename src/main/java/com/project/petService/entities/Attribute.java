package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Table(name="attribute")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    String name;

    Long price;

    int percentDiscount;

    @ManyToOne
    @JoinColumn(name = "productId")
    Product product;

    @ManyToOne
    @JoinColumn(name = "attributeTypeId")
    AttributeType attributeType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attribute")
    List<AttributeSize> attributeSize;
}
