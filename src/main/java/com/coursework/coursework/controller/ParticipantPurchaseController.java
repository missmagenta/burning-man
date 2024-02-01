package com.coursework.coursework.controller;

import com.coursework.coursework.dto.EventIdDTO;
import com.coursework.coursework.dto.PartPurchaseDTO;
import com.coursework.coursework.dto.SalesIdDTO;
import com.coursework.coursework.model.payload.Payload;
import com.coursework.coursework.service.impl.ParticipantPurchaseService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(
        value = "/purchase",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ParticipantPurchaseController {
    private final ParticipantPurchaseService participantPurchaseService;

    public ParticipantPurchaseController(ParticipantPurchaseService participantPurchaseService) {
        this.participantPurchaseService = participantPurchaseService;
    }

    @PostMapping("/buy")
    public ResponseEntity<Payload> buy(
            @Valid @RequestBody PartPurchaseDTO partPurchaseDTO) {
        Payload payload = participantPurchaseService.buyTicket(partPurchaseDTO);
        if (payload.code() != 200) {
            return ResponseEntity.badRequest().body(payload);
        }
        return ResponseEntity.ok(payload);
    }

    @GetMapping("tickets/{person_id}")
    public ResponseEntity<Payload> getTickets(@PathVariable("person_id") Integer personId) {
        Payload payload = participantPurchaseService.getBoughtTicket(personId);
        if (payload.code() != 200) {
            return ResponseEntity.badRequest().body(payload);
        }
        return ResponseEntity.ok(payload);
    }

    @PostMapping("participants")
    public ResponseEntity<Payload> getParticipants(
            @Valid @RequestBody EventIdDTO eventIdDTO
    ) {
        Payload payload = participantPurchaseService.getAllParticipants(eventIdDTO);
        if (payload.code() != 200) {
            return ResponseEntity.badRequest().body(payload);
        }
        return ResponseEntity.ok(payload);
    }

    @PostMapping("/see")
    public ResponseEntity<Payload> getParticipantsPerSale(
            @Valid @RequestBody SalesIdDTO salesIdDTO) {
        Payload payload = participantPurchaseService.getParticipantsPerSale(salesIdDTO);
        if (payload.code() != 200) {
            return ResponseEntity.badRequest().body(payload);
        }
        return ResponseEntity.ok(payload);
    }
}
