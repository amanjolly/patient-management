package com.pm.patientservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PatientService patientService;

    @Test
    void createPatient_shouldReturnSuccessResponse_whenRequestIsValid() throws Exception {
        PatientRequestDTO validRequest = new PatientRequestDTO();
        validRequest.setName("John Doe");
        validRequest.setEmail("johndoe@example.com");
        validRequest.setAddress("123 Main Street");
        validRequest.setDateOfBirth("1990-10-10");
        validRequest.setRegistrationDate("2023-10-25");

        PatientResponseDTO responseDTO = new PatientResponseDTO();
        responseDTO.setId("1");
        responseDTO.setName("John Doe");
        responseDTO.setEmail("johndoe@example.com");
        responseDTO.setAddress("123 Main Street");
        responseDTO.setDateOfBirth("1990-10-10");

        Mockito.when(patientService.createPatient(any(PatientRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(responseDTO.getId()))
                .andExpect(jsonPath("$.name").value(responseDTO.getName()))
                .andExpect(jsonPath("$.email").value(responseDTO.getEmail()))
                .andExpect(jsonPath("$.address").value(responseDTO.getAddress()))
                .andExpect(jsonPath("$.dateOfBirth").value(responseDTO.getDateOfBirth()));
    }

    @Test
    void createPatient_shouldReturnBadRequest_whenRequestIsInvalid() throws Exception {
        PatientRequestDTO invalidRequest = new PatientRequestDTO();
        invalidRequest.setName(""); // Invalid name
        invalidRequest.setEmail("invalid-email"); // Invalid email
        invalidRequest.setAddress(""); // Invalid address
        invalidRequest.setDateOfBirth(""); // Invalid date of birth
        invalidRequest.setRegistrationDate(""); // Empty registration date

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}