package com.coursework.coursework.service.impl;

import com.coursework.coursework.dto.EventOrganizerDTO;
import com.coursework.coursework.dto.EventStatusDTO;
import com.coursework.coursework.model.payload.BasePayload;
import com.coursework.coursework.model.payload.Payload;
import com.coursework.coursework.model.payload.PayloadWithCollection;
import com.coursework.coursework.repository.EventManagementProcessRepository;
import org.springframework.stereotype.Service;


@Service
public class EventManagementProcessService {
    private final EventManagementProcessRepository eventManagementProcessRepository;

    public EventManagementProcessService(EventManagementProcessRepository eventManagementProcessRepository) {
        this.eventManagementProcessRepository = eventManagementProcessRepository;
    }

    public Payload assignOrganizerToEvent(EventOrganizerDTO eventOrganizerDTO) {
        System.out.println(eventOrganizerDTO.getEventId() + eventOrganizerDTO.getOrganizerId());
        eventManagementProcessRepository.assignOrganizerToEvent(eventOrganizerDTO.getEventId(), eventOrganizerDTO.getOrganizerId());
            return new BasePayload(200, "DONE");
    }
    public Payload updateStatus(EventStatusDTO eventStatusDTO) {
        System.out.println(eventStatusDTO.getEventId());
        eventManagementProcessRepository.updateStatus(eventStatusDTO.getEventId(), eventStatusDTO.getStatus());
        return new BasePayload(200, "DONE");
    }

    public Payload getAliveOrgs() {
        return new PayloadWithCollection<>(200,
                eventManagementProcessRepository.findAliveOrgs());
    }

    public Payload getFutureEvents() {
        return new PayloadWithCollection<>(200,
                eventManagementProcessRepository.findFutureEvents());
    }

    public Payload getFutureOme() {
        return new PayloadWithCollection<>(200,
                eventManagementProcessRepository.findFutureOme());
    }
}
