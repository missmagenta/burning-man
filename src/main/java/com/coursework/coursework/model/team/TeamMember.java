package com.coursework.coursework.model.team;

import com.coursework.coursework.model.person.entity.Person;
import lombok.Data;

@Data
public class TeamMember {
    private Integer id;
    private Integer personId;
    private Integer teamId;

    private Team team;
    private Person person;
}
