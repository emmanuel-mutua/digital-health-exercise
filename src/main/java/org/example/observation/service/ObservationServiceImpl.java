package org.example.observation.service;

import lombok.RequiredArgsConstructor;
import org.example.common.exception.ApiException;
import org.example.common.response.DhGenericResponse;
import org.example.observation.dto.ObservationResponse;
import org.example.observation.repository.ObservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ObservationServiceImpl implements ObservationService {

    private final ObservationRepository observationRepository;

    @Override
    public DhGenericResponse<?> getObservationsByPatientId(Long patientId) {
        try {
            List<ObservationResponse> observationResponse = observationRepository.findByPatient_Id(patientId)
                    .stream()
                    .map(obs -> ObservationResponse.builder()
                            .id(obs. getId())
                            .date(obs.getDate())
                            .type(obs.getType())
                            .value(obs.getValue())
                            .build()
                    )
                    .toList();

            return DhGenericResponse.builder()
                    .success(true)
                    .data(observationResponse)
                    .message("Observations fetched successfully")
                    .build();
        } catch (Exception e) {
            throw new ApiException("Failed to fetch user observations", e);
        }
    }
}
