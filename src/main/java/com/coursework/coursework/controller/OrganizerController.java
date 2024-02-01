package com.coursework.coursework.controller;

import com.coursework.coursework.model.payload.Payload;
import com.coursework.coursework.service.impl.EventArchiveService;
import com.coursework.coursework.service.impl.EventManagementProcessService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/organizers",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class OrganizerController {
    private final EventManagementProcessService eventManagementProcessService;

    public OrganizerController(EventManagementProcessService eventManagementProcessService) {
        this.eventManagementProcessService = eventManagementProcessService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<Payload> getAliveOrgs() {
        return ResponseEntity.ok(eventManagementProcessService.getAliveOrgs());
    }
}
