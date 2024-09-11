package com.project.petService.entities;

import jakarta.persistence.*;
import lombok.*;

//@Table(name="medicalHistory")
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
public class MedicalHistory {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "medicalHistoryId")
    Long id;

    String diagnosis;

    String treatment;

    String notes;


}
