package com.coursework.coursework.service.impl;

import com.coursework.coursework.dto.ArtApplicationDTO;
import com.coursework.coursework.model.art.entity.ArtApplication;
import com.coursework.coursework.model.art.payload.ArtApplicationPayload;
import com.coursework.coursework.model.event.entity.Event;
import com.coursework.coursework.model.participant.entity.Participant;
import com.coursework.coursework.model.payload.BasePayload;
import com.coursework.coursework.model.payload.Payload;
import com.coursework.coursework.model.payload.PayloadWithCollection;
import com.coursework.coursework.repository.ArtApplicationRepository;
import com.coursework.coursework.repository.EventRepository;
import com.coursework.coursework.repository.ParticipantRepository;
import com.coursework.coursework.repository.helper.QueryFetchHelper;
import com.coursework.coursework.utils.ArtApplicationConverter;
import org.jooq.exception.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class ArtApplicationService {
    private final ArtApplicationRepository artApplicationRepository;
    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;

    public ArtApplicationService(ArtApplicationRepository artApplicationRepository, ParticipantRepository participantRepository, EventRepository eventRepository) {
        this.artApplicationRepository = artApplicationRepository;
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
    }

    public Payload getArtApplicationsByPerson(Integer personId) {
        return getArtApplicationsByPersonWrapper(personId, artApplicationRepository::findByPersonId);
    }

    public Payload getArtApplicationsByPersonWrapper(Integer personId,
                                              Function<Integer, List<ArtApplication>> sqlRequest) {
        if (personId == null) {
            return new BasePayload(400, "id человека не может быть null");
        }
        QueryFetchHelper<Integer, List<ArtApplication>> helper = new QueryFetchHelper<>(
                personId, sqlRequest
        );
        List<ArtApplication> records = helper.fetch().getFirst();
        if (records == null) {
            return new BasePayload(400, helper.fetch().getSecond());
        }
        List<ArtApplicationPayload> payload = new ArrayList<>();
        for (ArtApplication artApplication : records) {
            ArtApplicationPayload pay = ArtApplicationConverter.convertToPayload(
                    artApplication);
            payload.add(pay);
        }
        return new PayloadWithCollection<>(200, "Успешно", payload);
    }

    public Payload apply(ArtApplicationDTO artApplicationDTO) {
        Event event = eventRepository.findById(artApplicationDTO.getEventId());
        if (event == null) {
            return new BasePayload(400, "Event не найден");
        }

        Participant participant = participantRepository.getByPersonId(artApplicationDTO.getParticipantId());
        if (participant == null) {
            return new BasePayload(400, "Participant не найден");
        }
        System.out.println(artApplicationDTO.getTime());
        System.out.println(artApplicationDTO.getLight());
        try {
            artApplicationRepository.addApplication(
                    artApplicationDTO.getEventId(),
                    participant.getId(),
                    artApplicationDTO.getArtObject(),
                    artApplicationDTO.getDescription(),
                    artApplicationDTO.getMaterial(),
                    artApplicationDTO.getLight(),
                    artApplicationDTO.getTime(),
                    artApplicationDTO.getLocation(),
                    artApplicationDTO.getLink()
            );
        } catch (DataAccessException e) {
            return new BasePayload(400, "Ошибка при добавлении записи");
        } catch (DataIntegrityViolationException e) {
            return new BasePayload(400, "Некорректные данные");
        }
        return new BasePayload(200, "Успешно");

    }
}
