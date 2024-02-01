package com.coursework.coursework.controller;

import com.coursework.coursework.model.payload.Payload;
import com.coursework.coursework.service.impl.EventArchiveService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/archive-records",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class EventArchiveController {
    private final EventArchiveService eventArchiveService;

    public EventArchiveController(EventArchiveService eventArchiveService) {
        this.eventArchiveService = eventArchiveService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<Payload> getAllArchiveRecords() {
        return ResponseEntity.ok(eventArchiveService.getAllArchiveRecords());
    }

    @GetMapping(value = "/contents/{archive_record_id}")
    public ResponseEntity<Payload> getContents(@PathVariable("archive_record_id") Integer recordId) {
        return ResponseEntity.ok(eventArchiveService.getContents(recordId));
    }
}
