package com.hospital.controller;

import com.hospital.model.dto.request
        .MedicalRecordRequest;
import com.hospital.model.dto.response
        .ApiDataResponse;
import com.hospital.model.dto.response
        .MedicalRecordResponse;
import com.hospital.service
        .MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService
            medicalRecordService;

    @PostMapping(
            "/doctor/records")
    public ApiDataResponse
            <MedicalRecordResponse>
    create(
            @RequestBody
            MedicalRecordRequest request) {

        return ApiDataResponse
                .<MedicalRecordResponse>
                        builder()
                .success(true)
                .message(
                        "Create record success")
                .data(
                        medicalRecordService
                                .create(request))
                .build();
    }

    @GetMapping(
            "/patient/records")
    public ApiDataResponse
            <List<MedicalRecordResponse>>
    patientRecords() {

        return ApiDataResponse
                .<List<MedicalRecordResponse>>
                        builder()
                .success(true)
                .message(
                        "Patient records")
                .data(
                        medicalRecordService
                                .getPatientRecords())
                .build();
    }

    @GetMapping(
            "/doctor/records")
    public ApiDataResponse
            <List<MedicalRecordResponse>>
    doctorRecords() {

        return ApiDataResponse
                .<List<MedicalRecordResponse>>
                        builder()
                .success(true)
                .message(
                        "Doctor records")
                .data(
                        medicalRecordService
                                .getDoctorRecords())
                .build();
    }
}