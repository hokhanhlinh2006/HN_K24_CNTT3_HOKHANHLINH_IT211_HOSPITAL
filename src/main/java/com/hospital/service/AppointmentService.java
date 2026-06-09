package com.hospital.service;

import com.hospital.model.dto.request.AppointmentRequest;
import com.hospital.model.dto.response.AppointmentResponse;

import java.util.List;

public interface AppointmentService {

    AppointmentResponse createAppointment(
            AppointmentRequest request
    );

    List<AppointmentResponse>
    getPatientAppointments();

    List<AppointmentResponse>
    getDoctorAppointments();

    AppointmentResponse approveAppointment(
            Long id
    );

    AppointmentResponse rejectAppointment(
            Long id
    );
}