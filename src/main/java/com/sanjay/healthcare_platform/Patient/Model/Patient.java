package com.sanjay.healthcare_platform.Patient.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "patients", uniqueConstraints = {
    @UniqueConstraint(name = "uk_patients_email", columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    public Patient(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
