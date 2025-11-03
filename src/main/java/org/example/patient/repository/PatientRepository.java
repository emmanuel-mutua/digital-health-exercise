package org.example.patient.repository;

import org.example.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    @Query("SELECT p FROM Patient p WHERE "
            + "(:familyName IS NULL OR LOWER(p.familyName) LIKE LOWER(CONCAT('%', :familyName, '%'))) AND "
            + "(:givenName IS NULL OR LOWER(p.givenName) LIKE LOWER(CONCAT('%', :givenName, '%'))) AND "
            + "(:identifier IS NULL OR p.identifier = :identifier) AND "
            + "(:birthDate IS NULL OR p.birthDate = :birthDate)")
    List<Patient> searchPatients(
            @Param("familyName") String familyName,
            @Param("givenName") String givenName,
            @Param("identifier") String identifier,
            @Param("birthDate") LocalDate birthDate
    );

    Optional<Patient> findByIdentifier(Long identifier);
}
