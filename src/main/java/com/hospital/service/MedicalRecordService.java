package com.hospital.service;

import com.hospital.model.dto.request
        .MedicalRecordRequest;
import com.hospital.model.dto.response
        .MedicalRecordResponse;

import java.util.List;

public interface MedicalRecordService {

    MedicalRecordResponse create(
            MedicalRecordRequest request);

    List<MedicalRecordResponse>
    getPatientRecords();

    List<MedicalRecordResponse>
    getDoctorRecords();
}