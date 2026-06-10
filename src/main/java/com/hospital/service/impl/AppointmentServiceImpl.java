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
    public AppointmentResponse createAppointment(
            AppointmentRequest request) {

        System.out.println("CREATE APPOINTMENT API CALLED");
        System.out.println("Doctor ID: " + request.getDoctorId());
        System.out.println("Patient ID: " + request.getPatientId());
        System.out.println("Time: " + request.getAppointmentTime());

        User patient =
                userRepository
                        .findById(request.getPatientId())
                        .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + request.getPatientId()));

        User doctor =
                userRepository
                        .findById(
                                request.getDoctorId())
                        .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + request.getDoctorId()));

        boolean existed =
                appointmentRepository
                        .existsByDoctorIdAndAppointmentTime(
                                doctor.getId(),
                                request.getAppointmentTime());

        if (existed) {
            System.out.println("LOG -> Doctor already booked at this time");
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

        Appointment saved = appointmentRepository.save(appointment);
        System.out.println("LOG -> Appointment saved successfully with ID: " + saved.getId());

        return AppointmentMapper.toResponse(saved);
    }

    @Override
    // @PreAuthorize("hasRole('PATIENT')")
    public List<AppointmentResponse>
    getPatientAppointments() {

        // String username = SecurityUtils.getCurrentUsername();
        // User patient = userRepository.findByUsername(username).orElseThrow();
        // Use ID 3 (Patient One) if not authenticated for testing
        Long patientId = 3L;

        return appointmentRepository
                .findByPatientId(patientId)
                .stream()
                .map(
                        AppointmentMapper::toResponse)
                .toList();
    }

    @Override
    // @PreAuthorize("hasRole('DOCTOR')")
    public List<AppointmentResponse>
    getDoctorAppointments() {

        // String username = SecurityUtils.getCurrentUsername();
        // User doctor = userRepository.findByUsername(username).orElseThrow();
        // Use ID 2 (Doctor One) if not authenticated for testing
        Long doctorId = 2L;

        return appointmentRepository
                .findByDoctorId(doctorId)
                .stream()
                .map(
                        AppointmentMapper::toResponse)
                .toList();
    }

    @Override
    // @PreAuthorize("hasRole('DOCTOR')")
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
    // @PreAuthorize("hasRole('DOCTOR')")
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