package com.hospital.mapper;

import com.hospital.model.dto.response.AppointmentResponse;
import com.hospital.model.entity.Appointment;

public class AppointmentMapper {

    private AppointmentMapper() {
    }

    public static AppointmentResponse toResponse(
            Appointment appointment
    ) {

        return AppointmentResponse.builder()
                .id(appointment.getId())
                .patientName(
                        appointment.getPatient()
                                .getUsername())
                .doctorName(
                        appointment.getDoctor()
                                .getUsername())
                .appointmentTime(
                        appointment.getAppointmentTime())
                .symptomDescription(
                        appointment.getSymptomDescription())
                .status(
                        appointment.getStatus())
                .build();
    }
}