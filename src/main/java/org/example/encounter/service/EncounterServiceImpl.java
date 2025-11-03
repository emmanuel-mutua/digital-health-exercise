package org.example.encounter.service;

import lombok.RequiredArgsConstructor;
import org.example.common.exception.ApiException;
import org.example.common.response.DhGenericResponse;
import org.example.encounter.dto.CreateEncounter;
import org.example.encounter.dto.EncounterResponse;
import org.example.encounter.entity.Encounter;
import org.example.encounter.repository.EncounterRepository;
import org.example.patient.entity.Patient;
import org.example.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EncounterServiceImpl implements EncounterService {

    private final EncounterRepository encounterRepo;
    private final PatientRepository patientRepo;

    public DhGenericResponse<EncounterResponse> createEncounter(CreateEncounter dto) {
        try {
            Patient patient = patientRepo.findById(dto.getPatientId())
                    .orElseThrow(() -> new ApiException("Patient not found:"));

            Encounter encounter = Encounter.builder()
                    .patient(patient)
                    .start(dto.getStart())
                    .end(dto.getEnd())
                    .encounterClass(dto.getEncounterClass())
                    .build();

            Encounter saved = encounterRepo.save(encounter);
            return DhGenericResponse.<EncounterResponse>builder()
                    .success(true)
                    .message("Encounter created successfully")
                    .data(toEncounterResponse(saved))
                    .build();
        } catch (Exception ex) {
            return DhGenericResponse.<EncounterResponse>builder()
                    .success(false)
                    .message("Failed to create encounter")
                    .debugMessage(ex.getMessage())
                    .build();
        }
    }

    public DhGenericResponse<EncounterResponse> getEncounter(Long id) {
        return encounterRepo.findById(id)
                .map(e -> DhGenericResponse.<EncounterResponse>builder()
                        .success(true)
                        .message("Encounter fetched successfully")
                        .data(toEncounterResponse(e))
                        .build())
                .orElseGet(() -> DhGenericResponse.<EncounterResponse>builder()
                        .success(false)
                        .message("Encounter not found: " + id)
                        .build());
    }

    public DhGenericResponse<List<EncounterResponse>> getEncountersByPatient(Long patientId) {
        List<EncounterResponse> encounters = encounterRepo.findByPatient_Identifier(patientId)
                .stream()
                .map(this::toEncounterResponse)
                .collect(Collectors.toList());

        return DhGenericResponse.<List<EncounterResponse>>builder()
                .success(true)
                .message("Encounters for patient fetched successfully")
                .data(encounters)
                .build();
    }

    public DhGenericResponse<EncounterResponse> updateEncounter(Long code, CreateEncounter dto) {
        try {
            Encounter e = encounterRepo.findById(code)
                    .orElseThrow(() -> new ApiException("Encounter not found: " + code));

            if (!e.getPatient().getId().equals(dto.getPatientId())) {
                Patient p = patientRepo.findById(dto.getPatientId())
                        .orElseThrow(() -> new ApiException("Patient not found"));
                e.setPatient(p);
            }

            e.setStart(dto.getStart());
            e.setEnd(dto.getEnd());
            e.setEncounterClass(dto.getEncounterClass());

            Encounter updated = encounterRepo.save(e);
            return DhGenericResponse.<EncounterResponse>builder()
                    .success(true)
                    .message("Encounter updated successfully")
                    .data(toEncounterResponse(updated))
                    .build();
        } catch (Exception ex) {
            return DhGenericResponse.<EncounterResponse>builder()
                    .success(false)
                    .message("Failed to update encounter")
                    .debugMessage(ex.getMessage())
                    .build();
        }
    }

    public DhGenericResponse<Void> deleteEncounter(Long id) {
        if (!encounterRepo.existsById(id)) {
            return DhGenericResponse.<Void>builder()
                    .success(false)
                    .message("Encounter not found: " + id)
                    .build();
        }

        encounterRepo.deleteById(id);
        return DhGenericResponse.<Void>builder()
                .success(true)
                .message("Encounter deleted successfully")
                .build();
    }

    private EncounterResponse toEncounterResponse(Encounter e) {

        Patient patient = e.getPatient();

        return EncounterResponse.builder()
                .id(e.getId())
                .patientId(patient.getId())
                .patientIdentifier(patient.getIdentifier())
                .start(e.getStart())
                .end(e.getEnd())
                .encounterClass(e.getEncounterClass())
                .build();

    }
}