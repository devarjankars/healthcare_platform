package com.sanjay.healthcare_platform.Patient.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatientResponse {
    private final Long id;
    private final String name;
    private final String email;
}
