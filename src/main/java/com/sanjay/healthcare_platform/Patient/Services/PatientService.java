package com.sanjay.healthcare_platform.Patient.Services;

import com.sanjay.healthcare_platform.Patient.DTOs.CreatePatientRequest;
import com.sanjay.healthcare_platform.Patient.DTOs.PatientResponse;
import com.sanjay.healthcare_platform.Patient.DTOs.UpdatePatientRequest;
import com.sanjay.healthcare_platform.Patient.Model.Patient;
import com.sanjay.healthcare_platform.Patient.Repository.PatientRepository;
//import com.sanjay.healthcare_platform.common.exception.DuplicateResourceException;
import com.sanjay.healthcare_platform.common.exception.DuplicateResourceException;
import com.sanjay.healthcare_platform.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    @Transactional
    public PatientResponse createPatient(CreatePatientRequest request) {
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

    public List<PatientResponse> getAllPatients(Integer page, Integer size) {

        Page<Patient> patientPage = patientRepository.findAll(org.springframework.data.domain.PageRequest.of(page, size));
        return patientPage.map(this::toResponse).getContent();
    }

    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        return toResponse(patient);

    }

    public PatientResponse updatePatient(Long id, UpdatePatientRequest request) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        String normalizedEmail = request.getEmail().trim().toLowerCase(Locale.ROOT);
        if (!patient.getEmail().equals(normalizedEmail) && patientRepository.existsByEmail(normalizedEmail)) {
            throw new DuplicateResourceException("Patient with email '" + normalizedEmail + "' already exists");
        }

        patient.setName(request.getName().trim());
        patient.setEmail(normalizedEmail);
        Patient updatedPatient = patientRepository.save(patient);
        return toResponse(updatedPatient);
    }
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        patientRepository.delete(patient);
    }


//       helper to convert Patient entity to ResponsePatientRequest DTO
        private PatientResponse toResponse (Patient patient){
            return new PatientResponse(patient.getId(), patient.getName(), patient.getEmail());
        }


    }

