package com.coursework.coursework.controller;

import com.coursework.coursework.dto.EventRegistrationDTO;
import com.coursework.coursework.dto.RegIdDTO;
import com.coursework.coursework.model.payload.Payload;
import com.coursework.coursework.service.impl.EventRegistrationService;
import com.coursework.coursework.service.impl.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/reg",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@PreAuthorize("hasAnyAuthority('MAIN_IDEA_MANAGER', 'SALES_MANAGER')")
public class EventRegistrationController {
    private final EventRegistrationService eventRegistrationService;

    public EventRegistrationController(EventRegistrationService eventRegistrationService,
                                       TicketService ticketService) {
        this.eventRegistrationService = eventRegistrationService;
    }

    @PostMapping("/start")
    public ResponseEntity<Payload> startProcess(
            @Valid @RequestBody EventRegistrationDTO eventRegistrationDTO
    ) {
        Payload payload = eventRegistrationService.startProcess(eventRegistrationDTO);
        if (payload.code() != 200) {
            return ResponseEntity.badRequest().body(payload);
        }
        return ResponseEntity.ok(payload);
    }

    @PostMapping("/finish")
    public ResponseEntity<Payload> finishProcess(
            @Valid @RequestBody RegIdDTO finishRegDTO
    ) {
        Payload payload = eventRegistrationService.finishProcess(finishRegDTO);
        if (payload.code() != 200) {
            return ResponseEntity.badRequest().body(payload);
        }
        return ResponseEntity.ok(payload);
    }

    // work
    @GetMapping("/types/{eventId}")
    public ResponseEntity<Payload> getTicketRegistrations(
            @PathVariable("eventId") Integer eventId
    ) {
        Payload payload = eventRegistrationService.getAllRegistrationsPerEvent(eventId);
        if (payload.code() != 200) {
            return ResponseEntity.badRequest().body(payload);
        }
        return ResponseEntity.ok(payload);
    }

    // work
    @GetMapping("/org-types/{curOrgId}")
    public ResponseEntity<Payload> getTicketRegistrationsPerOrganizer(
            @PathVariable("curOrgId") Integer curOrgId
    ) {
        Payload payload = eventRegistrationService.getAllRegistrationsPerOrganizer(curOrgId);
        if (payload.code() != 200) {
            return ResponseEntity.badRequest().body(payload);
        }
        return ResponseEntity.ok(payload);
    }
}
