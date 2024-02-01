package com.coursework.coursework.controller;

import com.coursework.coursework.dto.PartRegDTO;
import com.coursework.coursework.dto.RegIdDTO;
import com.coursework.coursework.model.payload.Payload;
import com.coursework.coursework.service.impl.ParticipantRegistrationService;
import com.coursework.coursework.service.impl.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(
        value = "/registration",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ParticipantRegistrationController {
    private final ParticipantRegistrationService participantRegistrationService;
    private final TicketService ticketService;

    public ParticipantRegistrationController(ParticipantRegistrationService participantRegistrationService, TicketService ticketService) {
        this.participantRegistrationService = participantRegistrationService;
        this.ticketService = ticketService;
    }

    @PostMapping("/register")
    public ResponseEntity<Payload> register(
            @Valid @RequestBody PartRegDTO partRegDTO) {
        Payload payload = participantRegistrationService.register(partRegDTO);
        if (payload.code() != 200) {
            return ResponseEntity.badRequest().body(payload);
        }
        return ResponseEntity.ok(payload);
    }

    @GetMapping("/records/{personId}")
    public ResponseEntity<Payload> getRecords(
            @PathVariable("personId") Integer personId) {
        Payload payload = participantRegistrationService.getPersonRegistrations(personId);
        if (payload.code() != 200) {
            return ResponseEntity.badRequest().body(payload);
        }
        return ResponseEntity.ok(payload);
    }

    @PostMapping("/see")
    public ResponseEntity<Payload> getRegisteredPeople(
            @RequestBody RegIdDTO finishRegDTO) {
        Payload payload = participantRegistrationService.getEventRegistrations(finishRegDTO);
        if (payload.code() != 200) {
            return ResponseEntity.badRequest().body(payload);
        }
        return ResponseEntity.ok(payload);
    }

    @GetMapping("/")
    public ResponseEntity<Payload> getTickets() {
        return ResponseEntity.ok(ticketService.getTickets());
    }
}
