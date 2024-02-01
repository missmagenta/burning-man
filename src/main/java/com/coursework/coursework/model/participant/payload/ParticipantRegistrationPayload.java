package com.coursework.coursework.model.participant.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParticipantRegistrationPayload {
    private Integer id;
    private Integer personId;
    private String eventName;
    private Integer eventRegistrationId;
    private LocalDateTime registrationDateTime;
    private String ticketName;
    private String ticketDescription;
    private Double price;
    private String personName;
    private String personSurname;
    private String personEmail;

}
