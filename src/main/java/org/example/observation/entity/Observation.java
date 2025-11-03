package org.example.observation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.patient.entity.Patient;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dh_observations")
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "obs_id")
    private Long id;

    @Column(name = "obs_date")
    private LocalDateTime date;

    @Column(name = "obs_type")
    private String type;

    @Column(name = "obs_value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pat_id")
    private Patient patient;
}