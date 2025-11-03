package org.example.patient.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.patient.enums.Gender;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dh_patients", indexes = {
        @Index(name = "idx_pat_code", columnList = "pat_id"),
        @Index(name = "idx_pat_fam_name", columnList = "pat_fam_name"),
        @Index(name = "idx_pat_given_name", columnList = "pat_given_name"),
        @Index(name = "idx_pat_birth_date", columnList = "pat_birth_date")
})
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pat_id")
    private Long id;

    @NotNull(message = "Identifier is required")
    @Column(name = "pat_identifier", nullable = false, unique = true)
    private Long identifier;

    @NotBlank(message = "Given name is required")
    @Column(name = "pat_given_name", nullable = false)
    private String givenName;

    @NotBlank(message = "Family name is required")
    @Column(name = "pat_fam_name", nullable = false)
    private String familyName;

    @NotNull(message = "Birth date is required")
    @Column(name = "pat_birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "pat_gender", nullable = false)
    private Gender gender;

}
