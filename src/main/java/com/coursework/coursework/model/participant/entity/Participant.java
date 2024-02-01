package com.coursework.coursework.model.participant.entity;

import com.coursework.coursework.model.person.entity.Person;
import lombok.Data;

@Data
public class Participant {
    private Integer id;
    private Integer participantPurchaseId;
    private String documentDetails;
    private Integer personId;

    private ParticipantPurchase participantPurchase;
    private Person person;
}
