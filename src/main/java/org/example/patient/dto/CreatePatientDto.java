package org.example.patient.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePatientDto {

    @NotNull(message = "Identifier is required")
    @Positive(message = "Identifier must be a positive number")
    private Long identifier;

    @NotBlank(message = "Given name is required")
    private String givenName;

    @NotBlank(message = "Family name is required")
    private String familyName;

    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;

    @NotBlank(message = "Gender is required")
    private String gender;
}
