package org.example.patient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.response.DhGenericResponse;
import org.example.encounter.service.EncounterService;
import org.example.observation.service.ObservationService;
import org.example.patient.dto.CreatePatientDto;
import org.example.patient.entity.Patient;
import org.example.patient.enums.Gender;
import org.example.patient.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PatientController.class)
@AutoConfigureMockMvc(addFilters = false) // have disabled security filters for the slice test
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    @MockBean
    private EncounterService encounterService;

    @MockBean
    private ObservationService observationService;

    private Patient savedPatient;
    private CreatePatientDto createDto;

    @BeforeEach
    void setUp() {
        savedPatient = Patient.builder()
                .id(1L)
                .identifier(12345L)
                .givenName("Emmanuel")
                .familyName("Mutua")
                .birthDate(LocalDate.of(1980, 1, 1))
                .gender(Gender.MALE)
                .build();

        createDto = CreatePatientDto.builder()
                .identifier(12345L)
                .givenName("Emmanuel")
                .familyName("Mutua")
                .birthDate(LocalDate.of(1980, 1, 1))
                .gender("male")
                .build();
    }

    @Test
    void createPatient_returnsCreatedPatient() throws Exception {
        DhGenericResponse<Patient> resp = DhGenericResponse.<Patient>builder()
                .success(true)
                .message("Patient created successfully")
                .data(savedPatient)
                .build();

        when(patientService.createPatient(any(CreatePatientDto.class))).thenReturn(resp);

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    void getPatientById_returnsPatient() throws Exception {
        DhGenericResponse<Patient> resp = DhGenericResponse.<Patient>builder()
                .success(true)
                .message("Patient retrieved successfully")
                .data(savedPatient)
                .build();

        when(patientService.getPatientById(1L)).thenReturn(resp);

        mockMvc.perform(get("/api/patients/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1));
    }
}
