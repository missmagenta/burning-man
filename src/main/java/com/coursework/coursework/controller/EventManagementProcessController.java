package com.coursework.coursework.controller;

import com.coursework.coursework.dto.EventIdDTO;
import com.coursework.coursework.dto.EventOrganizerDTO;
import com.coursework.coursework.dto.EventSalesDTO;
import com.coursework.coursework.dto.EventStatusDTO;
import com.coursework.coursework.model.payload.Payload;
import com.coursework.coursework.service.impl.EventManagementProcessService;
import jakarta.validation.Valid;
import org.jooq.impl.QOM;
import org.jooq.meta.derby.sys.Sys;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(
        value = "/event-management",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@PreAuthorize("hasAnyAuthority('MAIN_IDEA_MANAGER')")
public class EventManagementProcessController {
    private final EventManagementProcessService eventManagementProcessService;

    public EventManagementProcessController(EventManagementProcessService eventManagementProcessService) {
        this.eventManagementProcessService = eventManagementProcessService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<Payload> getFutureOme() {
        return ResponseEntity.ok(eventManagementProcessService.getFutureOme());
    }

    // work
    @PostMapping("/assign")
    public ResponseEntity<Payload> assignOrganizerToEvent(
            @Valid @RequestBody EventOrganizerDTO eventOrganizerDTO) {
        Integer eventId = eventOrganizerDTO.getEventId();
        System.out.println(eventId);
        Integer organizerId = eventOrganizerDTO.getOrganizerId();
        System.out.println(organizerId);
        Payload payload = eventManagementProcessService.assignOrganizerToEvent(eventOrganizerDTO);
        if (payload.code() != 200) {
            return ResponseEntity.badRequest().body(payload);
        }
        return ResponseEntity.ok(payload);
    }

    @PostMapping("/update-status")
    public ResponseEntity<Payload> updateStatus(
            @Valid @RequestBody EventStatusDTO eventStatusDTO) {
        Payload payload = eventManagementProcessService.updateStatus(eventStatusDTO);
        if (payload.code() != 200) {
            return ResponseEntity.badRequest().body(payload);
        }
        return ResponseEntity.ok(payload);
    }
}
