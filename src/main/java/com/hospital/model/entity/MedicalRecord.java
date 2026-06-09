package com.hospital.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medical_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecord
        extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy =
                    GenerationType.IDENTITY)
    private Long id;

    @Column(
            columnDefinition = "TEXT")
    private String diagnosis;

    @Column(
            columnDefinition = "TEXT")
    private String prescription;

    @Column(
            columnDefinition = "TEXT")
    private String notes;

    @OneToOne
    @JoinColumn(
            name = "appointment_id")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(
            name = "patient_id")
    private User patient;

    @ManyToOne
    @JoinColumn(
            name = "doctor_id")
    private User doctor;
}