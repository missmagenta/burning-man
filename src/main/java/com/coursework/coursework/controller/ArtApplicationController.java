package com.coursework.coursework.controller;

import com.coursework.coursework.dto.ArtApplicationDTO;
import com.coursework.coursework.model.payload.Payload;
import com.coursework.coursework.service.impl.ArtApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/applications", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyAuthority('PARTICIPANT')")
public class ArtApplicationController {

    private final ArtApplicationService artApplicationService;

    public ArtApplicationController(ArtApplicationService artApplicationService) {
        this.artApplicationService = artApplicationService;
    }

    @GetMapping("/records/{person_id}")
    public ResponseEntity<Payload> getArtInstallations(@PathVariable("person_id") Integer person_id) {
        return ResponseEntity.ok(artApplicationService.getArtApplicationsByPerson(person_id));
    }

    @PostMapping("/apply")
    public ResponseEntity<Payload> apply(
            @Valid @RequestBody ArtApplicationDTO artApplicationDTO) {
        return ResponseEntity.ok(artApplicationService.apply(artApplicationDTO));
    }

}
