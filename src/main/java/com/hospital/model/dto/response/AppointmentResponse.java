package com.hospital.model.dto.response;

import com.hospital.model.enums.AppointmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {

    private Long id;

    private String patientName;

    private String doctorName;

    private LocalDateTime appointmentTime;

    private String symptomDescription;

    private AppointmentStatus status;
}