package com.coursework.coursework.model.organizer.entity;

import com.coursework.coursework.model.person.entity.Person;
import lombok.Data;

@Data
public class Organizer {
    private Integer id;
    private Integer personId;
    private Integer roleId;

    private Role role;
    private Person person;
}
