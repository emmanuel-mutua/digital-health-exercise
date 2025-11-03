package org.example.observation.repository;

import org.example.observation.entity.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationRepository extends JpaRepository<Observation,Long> {
    List<Observation> findByPatient_Id(Long patientId);
}
