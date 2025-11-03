package org.example.encounter.repository;

import org.example.encounter.entity.Encounter;
import org.example.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncounterRepository extends JpaRepository<Encounter,Long> {
    List<Encounter> findByPatient(Patient patient);

    List<Encounter> findByPatient_Identifier(Long patientIdentifier);
}
