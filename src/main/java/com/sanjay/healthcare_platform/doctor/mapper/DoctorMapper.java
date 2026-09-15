package com.sanjay.healthcare_platform.doctor.mapper;

import com.sanjay.healthcare_platform.doctor.dto.CreateDoctorRequest;
import com.sanjay.healthcare_platform.doctor.dto.ResponseDoctorRequest;
import com.sanjay.healthcare_platform.doctor.entity.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public Doctor toEntity(CreateDoctorRequest request) {
        Doctor doctor = new Doctor();
        doctor.setName(request.getName());
        doctor.setEmail(request.getEmail());
        doctor.setSpecialization(request.getSpecialization());
        return doctor;
    }

    public ResponseDoctorRequest toResponse(Doctor doctor) {
        return ResponseDoctorRequest.builder()
            .id(doctor.getId())
            .name(doctor.getName())
            .email(doctor.getEmail())
            .specialization(doctor.getSpecialization())
            .build();
    }
}
