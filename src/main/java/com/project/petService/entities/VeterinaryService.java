package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

//@Table(name="veterinaryServices")
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
public class VeterinaryService { // Cái này đang tính dùng chung với thằng
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "veterinaryServiceId")
    Long id;

    String name;
}
