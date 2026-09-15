package com.sanjay.healthcare_platform.Patient.Services;

import com.sanjay.healthcare_platform.Patient.DTOs.CreatePatientRequest;
import com.sanjay.healthcare_platform.Patient.DTOs.ResponsePatientRequest;
import com.sanjay.healthcare_platform.Patient.Model.Patient;
import com.sanjay.healthcare_platform.Patient.Repository.PatientRepository;
import com.sanjay.healthcare_platform.common.exception.DuplicateResourceException;
import com.sanjay.healthcare_platform.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    @Transactional
    public ResponsePatientRequest createPatient(CreatePatientRequest request) {
       if (request == null) {
           throw new IllegalArgumentException("Request cannot be null");
       }

       String normalizedEmail = request.getEmail().trim().toLowerCase(Locale.ROOT);
       if (patientRepository.existsByEmail(normalizedEmail)) {
           throw new DuplicateResourceException("Patient with email '" + normalizedEmail + "' already exists");
       }

       Patient patient = new Patient(request.getName().trim(), normalizedEmail);
       Patient savedPatient = patientRepository.save(patient);
       return toResponse(savedPatient);
    }

    public List<ResponsePatientRequest> getAllPatients() {
       return patientRepository.findAll().stream()
           .map(this::toResponse)
           .toList();
    }

    public ResponsePatientRequest getPatientById(Long id) {
       Patient patient = patientRepository.findById(id)
           .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
       return toResponse(patient);
    }

    private ResponsePatientRequest toResponse(Patient patient) {
       return new ResponsePatientRequest(patient.getId(), patient.getName(), patient.getEmail());
    }
}
