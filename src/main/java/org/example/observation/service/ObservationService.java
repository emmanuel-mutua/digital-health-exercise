package org.example.observation.service;

import org.example.common.response.DhGenericResponse;

public interface ObservationService {
    DhGenericResponse<?> getObservationsByPatientId(Long patientId);
}
