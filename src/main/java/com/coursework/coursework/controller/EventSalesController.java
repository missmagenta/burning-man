package com.coursework.coursework.controller;

import com.coursework.coursework.dto.EventSalesDTO;
import com.coursework.coursework.dto.SalesIdDTO;
import com.coursework.coursework.model.payload.Payload;
import com.coursework.coursework.model.purchase.entity.EventTicketSales;
import com.coursework.coursework.service.impl.EventTicketSalesService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(
        value = "/sales",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@PreAuthorize("hasAnyAuthority('MAIN_IDEA_MANAGER', 'SALES_MANAGER')")
public class EventSalesController {
    private final EventTicketSalesService eventSalesService;

    public EventSalesController(EventTicketSalesService eventSalesService) {
        this.eventSalesService = eventSalesService;
    }

    @PostMapping("/start")
    public ResponseEntity<Payload> startProcess(
            @Valid @RequestBody EventSalesDTO eventSalesDTO
    ) {
        Payload payload = eventSalesService.startProcess(eventSalesDTO);
        if (payload.code() != 200) {
            return ResponseEntity.badRequest().body(payload);
        }
        return ResponseEntity.ok(payload);
    }

    @PostMapping("/finish")
    public ResponseEntity<Payload> finishProcess(
            @Valid @RequestBody SalesIdDTO salesIdDTO) {
        System.out.println(salesIdDTO.getSalesId());
        Payload payload = eventSalesService.finishProcess(salesIdDTO);
        if (payload.code() != 200) {
            return ResponseEntity.badRequest().body(payload);
        }
        return ResponseEntity.ok(payload);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<Payload> getTicketSalesPerEvent(
            @PathVariable("eventId") Integer eventId
    ) {
        Payload payload = eventSalesService.getAllSalesPerEvent(eventId);
        if (payload.code() != 200) {
            return ResponseEntity.badRequest().body(payload);
        }
        return ResponseEntity.ok(payload);
    }

    @GetMapping("/types/{curOrgId}")
    public ResponseEntity<Payload> getSaleByRegistration(
            @PathVariable("curOrgId") Integer orgId
    ) {
        Payload payload = eventSalesService.getSalePerOrg(orgId);
        if (payload.code() != 200) {
            return ResponseEntity.badRequest().body(payload);
        }
        return ResponseEntity.ok(payload);
    }
}
