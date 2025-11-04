package org.example.patient.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDTO {

    @NotNull(message = "Id is required")
    private Long id;

    private Long identifier;

    private String givenName;

    private String familyName;

    private LocalDate birthDate;

    private String gender;
}