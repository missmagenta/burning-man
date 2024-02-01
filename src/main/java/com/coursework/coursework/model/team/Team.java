package com.coursework.coursework.model.team;

import com.coursework.coursework.model.organizer.entity.Organizer;
import lombok.Data;

@Data
public class Team {
    private Integer id;
    private Integer organizerId;
    private String name;

    private Organizer organizer;

}
