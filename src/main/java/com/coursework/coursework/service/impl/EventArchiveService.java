package com.coursework.coursework.service.impl;

import com.coursework.coursework.model.event.entity.Event;
import com.coursework.coursework.model.event.entity.EventArchive;
import com.coursework.coursework.model.event.entity.MainTheme;
import com.coursework.coursework.model.event.payload.EventArchivePayload;
import com.coursework.coursework.model.payload.BasePayload;
import com.coursework.coursework.model.payload.Payload;
import com.coursework.coursework.model.payload.PayloadWithCollection;
import com.coursework.coursework.repository.EventArchiveRepository;
import com.coursework.coursework.repository.helper.QueryFetchHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventArchiveService {
    private final EventArchiveRepository eventArchiveRepository;

    @Autowired
    public EventArchiveService(EventArchiveRepository eventArchiveRepository) {
        this.eventArchiveRepository = eventArchiveRepository;
    }

    public Payload getAllArchiveRecords() {
        return new PayloadWithCollection<>(200,
                eventArchiveRepository.findAll());
    }

    public Payload getContents(Integer recordId) {
        QueryFetchHelper<Integer, List<EventArchive>> helper = new QueryFetchHelper<>(
                recordId,
                eventArchiveRepository::getEventContent
        );
        List<EventArchive> records = helper.fetch().getFirst();
        if (records == null) {
            return new BasePayload(400, "Ошибка при выполнении запроса");
        }

        List<EventArchivePayload> payload = new ArrayList<>();

        for (EventArchive eventArchive : records) {
            EventArchivePayload pay = convertToEventArchivePayload(eventArchive.getEvent(), eventArchive.getMainTheme());
            payload.add(pay);
        }

        return new PayloadWithCollection<>(200, payload);
    }

    private EventArchivePayload convertToEventArchivePayload(Event event, MainTheme mainTheme) {
        Integer eventId = event.getId();
        String name = event.getName();
        LocalDateTime startDate = event.getStartDate();
        LocalDateTime endDate = event.getEndDate();
        String location = event.getLocation();
        String coordinates = event.getCoordinates();
        Integer capacity = event.getCapacity();
        String eventStatus = event.getEventStatus().getDisplayStatus();
        String mainThemeName = mainTheme.getName();
        String mainThemeDescription = mainTheme.getDescription();

        EventArchivePayload payload = new EventArchivePayload();

        payload.setId(eventId);
        payload.setName(name);
        payload.setStartDate(startDate);
        payload.setEndDate(endDate);
        payload.setLocation(location);
        payload.setCoordinates(coordinates);
        payload.setCapacity(capacity);
        payload.setEventStatus(eventStatus);
        payload.setMainThemeName(mainThemeName);
        payload.setMainThemeDescription(mainThemeDescription);

        return payload;
    }
}
