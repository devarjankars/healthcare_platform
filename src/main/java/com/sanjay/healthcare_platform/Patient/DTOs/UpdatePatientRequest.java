package com.sanjay.healthcare_platform.Patient.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UpdatePatientRequest {

    Long Id;
    String name;
    String email;
}
