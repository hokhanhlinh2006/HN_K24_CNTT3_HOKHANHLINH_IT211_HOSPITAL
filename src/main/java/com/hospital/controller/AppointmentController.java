package com.hospital.controller;

import com.hospital.model.dto.request.AppointmentRequest;
import com.hospital.model.dto.response.ApiDataResponse;
import com.hospital.model.dto.response.AppointmentResponse;
import com.hospital.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/patient/appointments")
    public ApiDataResponse<AppointmentResponse> createAppointment(
            @RequestBody AppointmentRequest request
    ) {

        return ApiDataResponse.<AppointmentResponse>builder()
                .success(true)
                .message("Appointment created")
                .data(appointmentService.createAppointment(request))
                .build();
    }

    @GetMapping("/patient/appointments")
    public ApiDataResponse<List<AppointmentResponse>> patientAppointments() {

        return ApiDataResponse.<List<AppointmentResponse>>builder()
                .success(true)
                .message("Patient appointments")
                .data(appointmentService.getPatientAppointments())
                .build();
    }

    @GetMapping("/doctor/appointments")
    public ApiDataResponse<List<AppointmentResponse>> doctorAppointments() {

        return ApiDataResponse.<List<AppointmentResponse>>builder()
                .success(true)
                .message("Doctor appointments")
                .data(appointmentService.getDoctorAppointments())
                .build();
    }

    @PutMapping("/doctor/appointments/{id}/approve")
    public ApiDataResponse<AppointmentResponse> approve(
            @PathVariable Long id
    ) {

        return ApiDataResponse.<AppointmentResponse>builder()
                .success(true)
                .message("Approved")
                .data(appointmentService.approveAppointment(id))
                .build();
    }

    @PutMapping("/doctor/appointments/{id}/reject")
    public ApiDataResponse<AppointmentResponse> reject(
            @PathVariable Long id
    ) {

        return ApiDataResponse.<AppointmentResponse>builder()
                .success(true)
                .message("Rejected")
                .data(appointmentService.rejectAppointment(id))
                .build();
    }
}