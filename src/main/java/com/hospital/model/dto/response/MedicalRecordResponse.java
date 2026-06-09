package com.hospital.model.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordResponse {

    private Long id;

    private String patientName;

    private String doctorName;

    private String diagnosis;

    private String prescription;

    private String notes;
}