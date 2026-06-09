package com.hospital.service.impl;

import com.hospital.mapper
        .MedicalRecordMapper;
import com.hospital.model.dto.request
        .MedicalRecordRequest;
import com.hospital.model.dto.response
        .MedicalRecordResponse;
import com.hospital.model.entity.*;
import com.hospital.repository.*;
import com.hospital.security.util
        .SecurityUtils;
import com.hospital.service
        .MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access
        .prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl
        implements MedicalRecordService {

    private final MedicalRecordRepository
            medicalRecordRepository;

    private final AppointmentRepository
            appointmentRepository;

    private final UserRepository
            userRepository;

    @Override
    @PreAuthorize("hasRole('DOCTOR')")
    public MedicalRecordResponse create(
            MedicalRecordRequest request) {

        if (medicalRecordRepository
                .existsByAppointmentId(
                        request.getAppointmentId())) {

            throw new RuntimeException(
                    "Medical record already exists");
        }

        Appointment appointment =
                appointmentRepository
                        .findById(
                                request
                                        .getAppointmentId())
                        .orElseThrow();

        String username =
                SecurityUtils
                        .getCurrentUsername();

        User doctor =
                userRepository
                        .findByUsername(
                                username)
                        .orElseThrow();

        MedicalRecord record =
                MedicalRecord.builder()
                        .appointment(
                                appointment)
                        .patient(
                                appointment
                                        .getPatient())
                        .doctor(
                                doctor)
                        .diagnosis(
                                request
                                        .getDiagnosis())
                        .prescription(
                                request
                                        .getPrescription())
                        .notes(
                                request
                                        .getNotes())
                        .build();

        return MedicalRecordMapper
                .toResponse(
                        medicalRecordRepository
                                .save(record));
    }

    @Override
    @PreAuthorize("hasRole('PATIENT')")
    public List<MedicalRecordResponse>
    getPatientRecords() {

        String username =
                SecurityUtils
                        .getCurrentUsername();

        User patient =
                userRepository
                        .findByUsername(
                                username)
                        .orElseThrow();

        return medicalRecordRepository
                .findByPatientId(
                        patient.getId())
                .stream()
                .map(
                        MedicalRecordMapper
                                ::toResponse)
                .toList();
    }

    @Override
    @PreAuthorize("hasRole('DOCTOR')")
    public List<MedicalRecordResponse>
    getDoctorRecords() {

        String username =
                SecurityUtils
                        .getCurrentUsername();

        User doctor =
                userRepository
                        .findByUsername(
                                username)
                        .orElseThrow();

        return medicalRecordRepository
                .findByDoctorId(
                        doctor.getId())
                .stream()
                .map(
                        MedicalRecordMapper
                                ::toResponse)
                .toList();
    }
}