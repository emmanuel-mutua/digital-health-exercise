package org.example.encounter.service;

import org.example.common.response.DhGenericResponse;
import org.example.encounter.dto.CreateEncounter;
import org.example.encounter.dto.EncounterResponse;

import java.util.List;

public interface EncounterService {
    DhGenericResponse<EncounterResponse> createEncounter(CreateEncounter dto);

    DhGenericResponse<EncounterResponse> getEncounter(Long id);

    DhGenericResponse<List<EncounterResponse>> getEncountersByPatient(Long patientId);

    DhGenericResponse<EncounterResponse> updateEncounter(Long id, CreateEncounter dto);

    DhGenericResponse<Void> deleteEncounter(Long id);
}
