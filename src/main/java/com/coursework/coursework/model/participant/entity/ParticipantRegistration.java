package com.coursework.coursework.model.participant.entity;

import com.coursework.coursework.model.person.entity.Person;
import com.coursework.coursework.model.purchase.entity.EventRegistration;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParticipantRegistration {
    private Integer id;
    private Integer personId;
    private Integer eventRegistrationId;
    private LocalDateTime registrationDateTime;

    private Person person;
    private EventRegistration eventRegistration;
}
