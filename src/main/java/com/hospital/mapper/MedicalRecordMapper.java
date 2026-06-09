package com.hospital.mapper;

import com.hospital.model.dto.response
        .MedicalRecordResponse;
import com.hospital.model.entity
        .MedicalRecord;

public class MedicalRecordMapper {

    private MedicalRecordMapper() {
    }

    public static MedicalRecordResponse
    toResponse(
            MedicalRecord record) {

        return MedicalRecordResponse
                .builder()
                .id(record.getId())
                .patientName(
                        record.getPatient()
                                .getUsername())
                .doctorName(
                        record.getDoctor()
                                .getUsername())
                .diagnosis(
                        record.getDiagnosis())
                .prescription(
                        record.getPrescription())
                .notes(
                        record.getNotes())
                .build();
    }
}