package org.example.encounter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.patient.entity.Patient;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dh_encounters", indexes = {
        @Index(name = "idx_enct_pat_id", columnList = "pat_id"),
        @Index(name = "idx_enct_start", columnList = "enct_start"),
        @Index(name = "idx_enct_end", columnList = "enct_end")
})
public class Encounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enct_code")
    private Long id;

    @NotNull(message = "Patient reference is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pat_id", nullable = false)
    private Patient patient;

    @NotNull(message = "Start date/time is required")
    @Column(name = "enct_start", nullable = false)
    private LocalDateTime start;

    @Column(name = "enct_end")
    private LocalDateTime end;

    @NotNull(message = "Encounter class is required")
    @Column(name = "enct_class", length = 50, nullable = false)
    private String encounterClass;
}
