package org.example.encounter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEncounter {

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Start time is required")
    private LocalDateTime start;

    private LocalDateTime end;

    @NotBlank(message = "Encounter class is required")
    private String encounterClass;

    private String reason;
}
