package com.sanjay.healthcare_platform.Patient.Controller;

import com.sanjay.healthcare_platform.Patient.DTOs.CreatePatientRequest;
import com.sanjay.healthcare_platform.Patient.DTOs.ResponsePatientRequest;
import com.sanjay.healthcare_platform.Patient.Services.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<ResponsePatientRequest> createPatient(@Valid @RequestBody CreatePatientRequest request) {
        ResponsePatientRequest response = patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ResponsePatientRequest>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePatientRequest> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }
}



