package com.sanjay.healthcare_platform.doctor.service;

import com.sanjay.healthcare_platform.common.exception.DuplicateResourceException;
import com.sanjay.healthcare_platform.common.exception.ResourceNotFoundException;
import com.sanjay.healthcare_platform.doctor.dto.CreateDoctorRequest;
import com.sanjay.healthcare_platform.doctor.dto.ResponseDoctorRequest;
import com.sanjay.healthcare_platform.doctor.entity.Doctor;
import com.sanjay.healthcare_platform.doctor.mapper.DoctorMapper;
import com.sanjay.healthcare_platform.doctor.repository.DoctorRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Transactional
    public ResponseDoctorRequest createDoctor(CreateDoctorRequest request) {
        if (doctorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Doctor already exists with email: " + request.getEmail());
        }

        Doctor doctor = doctorMapper.toEntity(request);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toResponse(savedDoctor);
    }

    @Transactional
    public ResponseDoctorRequest updateDoctor(Long id, CreateDoctorRequest request) {
        Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        if (!doctor.getEmail().equalsIgnoreCase(request.getEmail())
            && doctorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Doctor already exists with email: " + request.getEmail());
        }

        doctor.setName(request.getName());
        doctor.setEmail(request.getEmail());
        doctor.setSpecialization(request.getSpecialization());

        return doctorMapper.toResponse(doctorRepository.save(doctor));
    }

    @Transactional
    public ResponseDoctorRequest deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        doctorRepository.delete(doctor);
        return doctorMapper.toResponse(doctor);
    }

    @Transactional(readOnly = true)
    public ResponseDoctorRequest getDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        return doctorMapper.toResponse(doctor);
    }

    @Transactional(readOnly = true)
    public List<ResponseDoctorRequest> getAllDoctors() {
        return doctorRepository.findAll().stream()
            .map(doctorMapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<ResponseDoctorRequest> searchDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecializationContaining(specialization).stream()
            .map(doctorMapper::toResponse)
            .toList();
    }
}
