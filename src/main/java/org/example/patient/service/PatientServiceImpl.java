package org.example.patient.service;

import lombok.RequiredArgsConstructor;
import org.example.common.exception.ApiException;
import org.example.common.response.DhGenericResponse;
import org.example.patient.dto.CreatePatientDto;
import org.example.patient.dto.PatientDTO;
import org.example.patient.entity.Patient;
import org.example.patient.enums.Gender;
import org.example.patient.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public DhGenericResponse<Patient> createPatient(CreatePatientDto dto) {
        try {
            Patient patient = Patient.builder()
                    .identifier(dto.getIdentifier())
                    .givenName(dto.getGivenName())
                    .familyName(dto.getFamilyName())
                    .birthDate(dto.getBirthDate())
                    .gender(Gender.valueOf(dto.getGender().toUpperCase()))
                    .build();

            Patient saved = patientRepository.save(patient);

            return DhGenericResponse.<Patient>builder()
                    .success(true)
                    .message("Patient created successfully")
                    .data(saved)
                    .build();
        } catch (Exception ex) {
            return DhGenericResponse.<Patient>builder()
                    .success(false)
                    .message("Failed to create patient")
                    .debugMessage(ex.getLocalizedMessage())
                    .build();
        }
    }

    @Override
    public DhGenericResponse<Patient> updatePatient(Long id, PatientDTO dto) {
        try {
            Patient existing = getPatient(id);

            if (dto.getIdentifier() != null) {
                existing.setIdentifier(dto.getIdentifier());
            }
            if (dto.getGivenName() != null && !dto.getGivenName().isBlank()) {
                existing.setGivenName(dto.getGivenName());
            }
            if (dto.getFamilyName() != null && !dto.getFamilyName().isBlank()) {
                existing.setFamilyName(dto.getFamilyName());
            }
            if (dto.getBirthDate() != null) {
                existing.setBirthDate(dto.getBirthDate());
            }
            if (dto.getGender() != null && !dto.getGender().isBlank()) {
                existing.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
            }

            Patient updated = patientRepository.save(existing);

            return DhGenericResponse.<Patient>builder()
                    .success(true)
                    .message("Patient updated successfully")
                    .data(updated)
                    .build();
        } catch (Exception ex) {
            return DhGenericResponse.<Patient>builder()
                    .success(false)
                    .message("Failed to update patient")
                    .debugMessage(ex.getMessage())
                    .build();
        }
    }

    @Override
    public DhGenericResponse<Patient> getPatientById(Long id) {
        try {
            Patient patient = getPatient(id);
            return DhGenericResponse.<Patient>builder()
                    .success(true)
                    .message("Patient retrieved successfully")
                    .data(patient)
                    .build();
        } catch (Exception ex) {
            return DhGenericResponse.<Patient>builder()
                    .success(false)
                    .message("Patient not found")
                    .debugMessage(ex.getMessage())
                    .build();
        }
    }

    @Override
    public DhGenericResponse<String> deletePatient(Long id) {
        try {
            Patient existing = getPatient(id);
            patientRepository.delete(existing);

            return DhGenericResponse.<String>builder()
                    .success(true)
                    .message("Patient deleted successfully")
                    .data("Deleted patient with ID: " + id)
                    .build();
        } catch (Exception ex) {
            return DhGenericResponse.<String>builder()
                    .success(false)
                    .message("Failed to delete patient")
                    .debugMessage(ex.getMessage())
                    .build();
        }
    }

    @Override
    public DhGenericResponse<List<Patient>> searchPatients(String familyName, String givenName, String identifier, LocalDate birthDate) {
        try {
            List<Patient> results = patientRepository.searchPatients(familyName, givenName, identifier, birthDate);
            return DhGenericResponse.<List<Patient>>builder()
                    .success(true)
                    .message("Search completed successfully")
                    .data(results)
                    .build();
        } catch (Exception ex) {
            return DhGenericResponse.<List<Patient>>builder()
                    .success(false)
                    .message("Failed to search patients")
                    .debugMessage(ex.getMessage())
                    .build();
        }
    }

    @Override
    public DhGenericResponse<?> getAllPatients(int page, int size, String sortBy) {
        try {

            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
            Page<Patient> patients = patientRepository.findAll(pageable);

            return DhGenericResponse.<Page<Patient>>builder()
                    .success(true)
                    .message("Patients retrieved successfully")
                    .data(patients)
                    .build();

        } catch (Exception ex) {
            return DhGenericResponse.<List<Patient>>builder()
                    .success(false)
                    .message("Failed to retrieve patients")
                    .debugMessage(ex.getMessage())
                    .build();
        }
    }

    private Patient getPatient(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ApiException("Patient not found with id: " + id));
    }
    }


