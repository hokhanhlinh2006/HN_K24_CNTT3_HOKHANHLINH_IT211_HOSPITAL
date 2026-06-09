package com.hospital.service.impl;

import com.hospital.mapper.AppointmentMapper;
import com.hospital.model.dto.request.AppointmentRequest;
import com.hospital.model.dto.response.AppointmentResponse;
import com.hospital.model.entity.Appointment;
import com.hospital.model.entity.User;
import com.hospital.model.enums.AppointmentStatus;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.UserRepository;
import com.hospital.security.util.SecurityUtils;
import com.hospital.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl
        implements AppointmentService {

    private final AppointmentRepository
            appointmentRepository;

    private final UserRepository
            userRepository;

    @Override
    @PreAuthorize("hasRole('PATIENT')")
    public AppointmentResponse createAppointment(
            AppointmentRequest request) {

        String username =
                SecurityUtils
                        .getCurrentUsername();

        User patient =
                userRepository
                        .findByUsername(username)
                        .orElseThrow();

        User doctor =
                userRepository
                        .findById(
                                request.getDoctorId())
                        .orElseThrow();

        boolean existed =
                appointmentRepository
                        .existsByDoctorIdAndAppointmentTime(
                                doctor.getId(),
                                request.getAppointmentTime());

        if (existed) {

            throw new RuntimeException(
                    "Doctor already booked");
        }

        Appointment appointment =
                Appointment.builder()
                        .appointmentTime(
                                request.getAppointmentTime())
                        .symptomDescription(
                                request.getSymptomDescription())
                        .status(
                                AppointmentStatus.PENDING)
                        .patient(patient)
                        .doctor(doctor)
                        .build();

        return AppointmentMapper.toResponse(
                appointmentRepository.save(
                        appointment));
    }

    @Override
    @PreAuthorize("hasRole('PATIENT')")
    public List<AppointmentResponse>
    getPatientAppointments() {

        String username =
                SecurityUtils
                        .getCurrentUsername();

        User patient =
                userRepository
                        .findByUsername(username)
                        .orElseThrow();

        return appointmentRepository
                .findByPatientId(
                        patient.getId())
                .stream()
                .map(
                        AppointmentMapper::toResponse)
                .toList();
    }

    @Override
    @PreAuthorize("hasRole('DOCTOR')")
    public List<AppointmentResponse>
    getDoctorAppointments() {

        String username =
                SecurityUtils
                        .getCurrentUsername();

        User doctor =
                userRepository
                        .findByUsername(username)
                        .orElseThrow();

        return appointmentRepository
                .findByDoctorId(
                        doctor.getId())
                .stream()
                .map(
                        AppointmentMapper::toResponse)
                .toList();
    }

    @Override
    @PreAuthorize("hasRole('DOCTOR')")
    public AppointmentResponse approveAppointment(
            Long id) {

        Appointment appointment =
                appointmentRepository
                        .findById(id)
                        .orElseThrow();

        appointment.setStatus(
                AppointmentStatus.APPROVED);

        return AppointmentMapper.toResponse(
                appointmentRepository.save(
                        appointment));
    }

    @Override
    @PreAuthorize("hasRole('DOCTOR')")
    public AppointmentResponse rejectAppointment(
            Long id) {

        Appointment appointment =
                appointmentRepository
                        .findById(id)
                        .orElseThrow();

        appointment.setStatus(
                AppointmentStatus.REJECTED);

        return AppointmentMapper.toResponse(
                appointmentRepository.save(
                        appointment));
    }
}