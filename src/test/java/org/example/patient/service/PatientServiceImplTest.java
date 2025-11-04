package org.example.patient.service;

import org.example.common.response.DhGenericResponse;
import org.example.patient.dto.CreatePatientDto;
import org.example.patient.entity.Patient;
import org.example.patient.enums.Gender;
import org.example.patient.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    private CreatePatientDto createDto;
    private Patient savedPatient;

    @BeforeEach
    void setUp() {
        createDto = CreatePatientDto.builder()
                .identifier(12345L)
                .givenName("Emmanuel")
                .familyName("Mutua")
                .birthDate(LocalDate.of(1980, 1, 1))
                .gender("male")
                .build();

        savedPatient = Patient.builder()
                .id(1L)
                .identifier(12345L)
                .givenName("Emmanuel")
                .familyName("Mutua")
                .birthDate(LocalDate.of(1980, 1, 1))
                .gender(Gender.MALE)
                .build();
    }

    @Test
    void createPatient_success() {
        when(patientRepository.save(any(Patient.class))).thenReturn(savedPatient);

        DhGenericResponse<Patient> resp = patientService.createPatient(createDto);

        assertThat(resp).isNotNull();
        assertThat(resp.isSuccess()).isTrue();
        assertThat(resp.getData()).isNotNull();
        assertThat(resp.getData().getId()).isEqualTo(1L);
        assertThat(resp.getData().getGivenName()).isEqualTo("Emmanuel");
        assertThat(resp.getData().getFamilyName()).isEqualTo("Mutua");
        assertThat(resp.getMessage()).contains("created");
    }

    @Test
    void getPatientById_found() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(savedPatient));

        DhGenericResponse<Patient> resp = patientService.getPatientById(1L);

        assertThat(resp).isNotNull();
        assertThat(resp.isSuccess()).isTrue();
        assertThat(resp.getData()).isEqualTo(savedPatient);
    }

}