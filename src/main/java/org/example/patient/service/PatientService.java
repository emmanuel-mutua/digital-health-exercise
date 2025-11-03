package org.example.patient.service;

import org.example.common.response.DhGenericResponse;
import org.example.patient.dto.CreatePatientDto;
import org.example.patient.dto.PatientDTO;
import org.example.patient.entity.Patient;

import java.time.LocalDate;
import java.util.List;

public interface PatientService {

    DhGenericResponse<Patient> createPatient(CreatePatientDto dto);

    DhGenericResponse<Patient> updatePatient(Long id, PatientDTO dto);

    DhGenericResponse<Patient> getPatientById(Long id);

    DhGenericResponse<String> deletePatient(Long id);

    DhGenericResponse<List<Patient>> searchPatients(String familyName, String givenName, String identifier, LocalDate birthDate);

    DhGenericResponse<?> getAllPatients(int page, int size, String sortBy);
}
