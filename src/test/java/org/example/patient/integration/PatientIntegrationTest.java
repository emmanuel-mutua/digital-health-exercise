package org.example.patient.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.patient.dto.CreatePatientDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.flyway.enabled=false"
})
public class PatientIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createAndRetrievePatient_endToEnd() throws Exception {
        CreatePatientDto dto = CreatePatientDto.builder()
                .identifier(55555L)
                .givenName("Emmanuel")
                .familyName("Mutua")
                .birthDate(LocalDate.of(1990, 5, 20))
                .gender("male")
                .build();

        // Create a new patient
        String createResp = mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // parse response to extract id
        var node = objectMapper.readTree(createResp);
        assertThat(node.get("success").asBoolean()).isTrue();
        var idNode = node.path("data").path("id");
        assertThat(idNode.isNumber()).isTrue();
        long id = idNode.asLong();

        // Retrieve by id
        String getResp = mockMvc.perform(get("/api/patients/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var getNode = objectMapper.readTree(getResp);
        assertThat(getNode.get("success").asBoolean()).isTrue();
        assertThat(getNode.path("data").path("identifier").asLong()).isEqualTo(55555L);
        assertThat(getNode.path("data").path("givenName").asText()).isEqualTo("Emmanuel");
        assertThat(getNode.path("data").path("familyName").asText()).isEqualTo("Mutua");
    }
}