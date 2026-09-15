package com.sanjay.healthcare_platform.doctor.repository;

import com.sanjay.healthcare_platform.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByEmail(String email);
    List<Doctor> findBySpecializationContaining(String specialization);
}
