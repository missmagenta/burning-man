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
@RequestMapping(value = "/future",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class FutureEventsController {
    private final EventManagementProcessService eventService;

    public FutureEventsController(EventManagementProcessService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<Payload> getFutureEvents() {
        return ResponseEntity.ok(eventService.getFutureEvents());
    }
}
