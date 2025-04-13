package com.pm.patientservice.dto;

import com.pm.patientservice.dto.validator.PatientRequestValidationGroup;
import jakarta.validation.constraints.*;

public class PatientRequestDTO {
    @NotBlank(message = "Name should not be blank")
    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters")
    private String name;
    @NotBlank(message = "Email should not be blank")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Address should not be blank")
    @Size(min = 5, max = 200, message = "Address should be between 5 and 200 characters")
    private String address;
    @NotBlank(message = "Date of birth should not be blank")
    private String dateOfBirth;
    @NotBlank(groups = PatientRequestValidationGroup.class, message = "Registration date should not be blank")
    private String registrationDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
}
