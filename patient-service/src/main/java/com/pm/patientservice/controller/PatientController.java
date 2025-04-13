package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.validator.PatientRequestValidationGroup;
import com.pm.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients") //http://localhost:4000/patients
@Tag(name = "Patient", description = "API's for managing Patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/all")
    @Operation(summary = "Get all patients Summary" , description = "Get all patients Description")
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        return ResponseEntity.ok().body(patientService.getAllPatients());
    }

    @PostMapping()
    @Operation(summary = "Create patient Summary" , description = "Create patient Description")
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, PatientRequestValidationGroup.class})
                                                                @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update patient Summary" , description = "Update patient Description",
            parameters = {@Parameter(name = "patientId", description = "Patient Id",
                    required =
                    true),
                    @Parameter(name = "PatientRequestDTO", description = "Patient Request DTO", required = true)})
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable("id") UUID patientId, @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(patientId, patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }


    @Operation(summary = "Delete patient Summary" , description = "Delete patient Description" ,
            parameters = {@Parameter(name = "patientId", description = "Patient Id of the patient to be deleted", required = true)})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deletePatient(@PathVariable("id") UUID patientId) {
        patientService.deletePatient(patientId);
        return ResponseEntity.noContent().build();
    }
}
