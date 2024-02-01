package com.coursework.coursework.model.user.container;

import java.time.LocalDate;

import com.coursework.coursework.model.person.entity.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpUser {
    @NotBlank
    @Size(min = 3, max = 30)
    private String name;
    @NotBlank
    @Size(min = 3, max = 30)
    private String surname;
    @NotBlank
    private String email;
    private String phoneNumber;
    @NotNull
    private Gender personGender;
    @NotNull
    private LocalDate birthDate;
    @NotBlank
    @Size(min = 3, max = 30)
    private String username;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
