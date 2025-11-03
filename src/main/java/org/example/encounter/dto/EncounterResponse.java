package org.example.encounter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncounterResponse {
    private Long id;
    private Long patientId;
    private Long patientIdentifier;
    private LocalDateTime start;
    private LocalDateTime end;
    private String encounterClass;
    private String reason;
}
