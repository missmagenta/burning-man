package com.coursework.coursework.model.person.entity;

import com.coursework.coursework.model.person.entity.Gender;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Person {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String contactNumber;
    private Gender gender;
    private LocalDate birthDate;
    @Nullable
    private LocalDate deathDate;
}
