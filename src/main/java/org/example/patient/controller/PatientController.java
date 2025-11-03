package org.example.patient.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.common.response.DhGenericResponse;
import org.example.encounter.service.EncounterService;
import org.example.observation.service.ObservationService;
import org.example.patient.dto.CreatePatientDto;
import org.example.patient.dto.PatientDTO;
import org.example.patient.entity.Patient;
import org.example.patient.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@Tag(name = "Patient", description = "Endpoints for managing patients: creation, retrieval, update, and deletion")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final EncounterService encounterService;
    private final ObservationService observationService;

    @Operation(summary = "Creates a new patient")
    @PostMapping
    public ResponseEntity<DhGenericResponse<Patient>> createPatient(@RequestBody CreatePatientDto dto) {
        return ResponseEntity.ok(patientService.createPatient(dto));
    }

    @Operation(summary = "Updates patient information")
    @PutMapping("/{id}")
    public ResponseEntity<DhGenericResponse<Patient>> updatePatientInfo(@PathVariable Long id, @RequestBody PatientDTO dto) {
        return ResponseEntity.ok(patientService.updatePatient(id, dto));
    }

    @Operation(summary = "Retrieves patient details by ID")
    @GetMapping("/{id}")
    public ResponseEntity<DhGenericResponse<Patient>> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @Operation(summary = "Deletes a patient by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<DhGenericResponse<String>> deletePatient(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.deletePatient(id));
    }

    @Operation(summary = "Searches for patients based on criteria")
    @GetMapping("/search")
    public ResponseEntity<DhGenericResponse<List<Patient>>> searchPatients(
            @RequestParam(required = false) String family,
            @RequestParam(required = false) String given,
            @RequestParam(required = false) String identifier,
            @RequestParam(required = false) LocalDate birthDate) {
        return ResponseEntity.ok(patientService.searchPatients(family, given, identifier, birthDate));
    }

    @Operation(summary = "Retrieves all patients")
    @GetMapping
    public ResponseEntity<DhGenericResponse<?>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(patientService.getAllPatients(page, size, sortBy));
    }

    @Operation(summary = "Retrieves all encounters for a specific patient")
    @GetMapping("/{id}/encounters")
    public ResponseEntity<DhGenericResponse<?>> getPatientEncounters(@PathVariable Long id) {
        DhGenericResponse<?> encounters = encounterService.getEncountersByPatient(id);
        return ResponseEntity.ok(encounters);
    }

    @Operation(summary = "Retrieves all observations for a specific patient")
    @GetMapping("/{id}/observations")
    public ResponseEntity<DhGenericResponse<?>> getPatientObservations(@PathVariable Long id) {
        return ResponseEntity.ok(observationService.getObservationsByPatientId(id));
    }

}
