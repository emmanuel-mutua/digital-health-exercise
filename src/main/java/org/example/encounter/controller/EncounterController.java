package org.example.encounter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.common.response.DhGenericResponse;
import org.example.encounter.dto.CreateEncounter;
import org.example.encounter.dto.EncounterResponse;
import org.example.encounter.service.EncounterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encounters")
@RequiredArgsConstructor
@Tag(name = "Encounter", description = "Manage patient encounters")
public class EncounterController {

    private final EncounterService encounterService;

    @Operation(
            summary = "Create a new encounter",
            description = "Registers a new encounter for a given patient."
    )
    @PostMapping
    public ResponseEntity<DhGenericResponse<EncounterResponse>> createEncounter(@Valid @RequestBody CreateEncounter dto) {
        var res = encounterService.createEncounter(dto);
        return ResponseEntity.ok(res);
    }

    @Operation(
            summary = "Retrieve a single encounter",
            description = "Fetch details of an encounter using its unique ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<DhGenericResponse<EncounterResponse>> getEncounter(@PathVariable Long id) {
        var res = encounterService.getEncounter(id);
        return ResponseEntity.ok(res);
    }

    @Operation(
            summary = "List encounters",
            description = "Fetch all encounters or filter by patient ID if provided."
    )
    @GetMapping
    public ResponseEntity<DhGenericResponse<List<EncounterResponse>>> getEncountersByPatient(
            @RequestParam(required = false) Long patientId) {

        var res = encounterService.getEncountersByPatient(patientId);
        return ResponseEntity.ok(res);
    }

    @Operation(
            summary = "Update an encounter",
            description = "Modify encounter details for the given encounter ID."
    )
    @PutMapping("/{id}")
    public ResponseEntity<DhGenericResponse<EncounterResponse>> updateEncounter(
            @PathVariable Long id,
            @Valid @RequestBody CreateEncounter dto) {

        var res = encounterService.updateEncounter(id, dto);
        return ResponseEntity.ok(res);
    }

    @Operation(
            summary = "Delete an encounter",
            description = "Removes the encounter with the given ID permanently."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<DhGenericResponse<Void>> deleteEncounter(@PathVariable Long id) {
        return ResponseEntity.ok(encounterService.deleteEncounter(id));
    }
}
