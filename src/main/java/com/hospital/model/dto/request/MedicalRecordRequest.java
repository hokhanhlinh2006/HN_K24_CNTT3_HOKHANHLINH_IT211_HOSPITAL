package com.hospital.model.dto.request;

import lombok.Data;

@Data
public class MedicalRecordRequest {

    private Long appointmentId;

    private String diagnosis;

    private String prescription;

    private String notes;
}