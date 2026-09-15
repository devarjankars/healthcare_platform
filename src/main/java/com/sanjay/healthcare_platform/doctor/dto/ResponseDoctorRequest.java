package com.sanjay.healthcare_platform.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDoctorRequest {
    private Long id;
    private String name;
    private String email;
    private String specialization;
}