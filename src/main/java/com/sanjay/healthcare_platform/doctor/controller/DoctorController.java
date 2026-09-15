package com.sanjay.healthcare_platform.doctor.controller;

import com.sanjay.healthcare_platform.doctor.dto.CreateDoctorRequest;
import com.sanjay.healthcare_platform.doctor.dto.ResponseDoctorRequest;
import com.sanjay.healthcare_platform.doctor.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping
    public ResponseDoctorRequest createDoctor( @Valid @RequestBody CreateDoctorRequest request) {
        return doctorService.createDoctor(request);
    }

    @GetMapping("/{id}")
    public ResponseDoctorRequest getDoctor(@PathVariable Long id) {
        return doctorService.getDoctor(id);
    }

    @GetMapping
    public List<ResponseDoctorRequest> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @PutMapping("/{id}")
    public ResponseDoctorRequest updateDoctor(@PathVariable Long id, @Valid @RequestBody CreateDoctorRequest request) {
        return doctorService.updateDoctor(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/{specialization}")
    public List<ResponseDoctorRequest> searchDoctorsBySpecialization(@PathVariable String specialization) {
        return doctorService.searchDoctorsBySpecialization(specialization);
    }
}
