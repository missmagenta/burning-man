package com.coursework.coursework.model.organizer.entity;

import com.coursework.coursework.model.organizer.entity.OrganizerRole;
import lombok.Data;

@Data
public class Role {
    private Integer id;
    private OrganizerRole organizerRole;
    private String description;
}
