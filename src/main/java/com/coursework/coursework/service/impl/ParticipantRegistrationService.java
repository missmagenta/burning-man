package com.coursework.coursework.service.impl;

import com.coursework.coursework.dto.PartRegDTO;
import com.coursework.coursework.dto.RegIdDTO;
import com.coursework.coursework.model.participant.entity.ParticipantRegistration;
import com.coursework.coursework.model.participant.payload.ParticipantRegistrationPayload;
import com.coursework.coursework.model.payload.BasePayload;
import com.coursework.coursework.model.payload.Payload;
import com.coursework.coursework.model.payload.PayloadWithCollection;
import com.coursework.coursework.model.person.entity.Person;
import com.coursework.coursework.repository.ParticipantRegistrationRepository;
import com.coursework.coursework.repository.PersonRepository;
import com.coursework.coursework.repository.helper.QueryFetchHelper;
import com.coursework.coursework.utils.ParticipantRegistrationConverter;
import org.jooq.exception.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class ParticipantRegistrationService {
    private final ParticipantRegistrationRepository participantRegistrationRepository;
    private final PersonRepository personRepository;

    public ParticipantRegistrationService(ParticipantRegistrationRepository participantRegistrationRepository, PersonRepository personRepository) {
        this.participantRegistrationRepository = participantRegistrationRepository;
        this.personRepository = personRepository;
    }

    public Payload getPersonRegistrations(Integer personId) {
        return getPersonRegistrationsWrapper(personId, participantRegistrationRepository::findByPersonId);
    }

    public Payload getPersonRegistrationsWrapper(Integer personId,
                                                 Function<Integer, List<ParticipantRegistration>> sqlRequest) {
        if (personId == null) {
            return new BasePayload(400, "id человека не может быть null");
        }
        Person person = personRepository.find(personId);
        if (person == null) {
            return new BasePayload(400, "Человек не найден");
        }

        QueryFetchHelper<Integer, List<ParticipantRegistration>> helper = new QueryFetchHelper<>(
                personId, sqlRequest
        );

        List<ParticipantRegistration> records = helper.fetch().getFirst();
        if (records == null) {
            return new BasePayload(400, helper.fetch().getSecond());
        }
        List<ParticipantRegistrationPayload> payload = new ArrayList<>();
        for (ParticipantRegistration participantRegistration : records) {
            ParticipantRegistrationPayload pay = ParticipantRegistrationConverter.convert(
                    participantRegistration,
                    participantRegistration.getEventRegistration(),
                    participantRegistration.getEventRegistration().getTicket(),
                    participantRegistration.getPerson());
            payload.add(pay);
        }
        return new PayloadWithCollection<>(200, "Успешно", payload);
    }

    public Payload getEventRegistrations(RegIdDTO finishRegDTO) {
        return getEventRegistrationsWrapper(finishRegDTO.getRegId(), participantRegistrationRepository::findByRegId);
    }

    public Payload getEventRegistrationsWrapper(Integer regId,
                                                 Function<Integer, List<ParticipantRegistration>> sqlRequest) {
        if (regId == null) {
            return new BasePayload(400, "id reg не может быть null");
        }

        QueryFetchHelper<Integer, List<ParticipantRegistration>> helper = new QueryFetchHelper<>(
                regId, sqlRequest
        );

        List<ParticipantRegistration> records = helper.fetch().getFirst();
        if (records == null) {
            return new BasePayload(400, helper.fetch().getSecond());
        }
        List<ParticipantRegistrationPayload> payload = new ArrayList<>();
        for (ParticipantRegistration participantRegistration : records) {
            ParticipantRegistrationPayload pay = ParticipantRegistrationConverter.convert(
                    participantRegistration,
                    participantRegistration.getEventRegistration(),
                    participantRegistration.getEventRegistration().getTicket(),
                    participantRegistration.getPerson());
            payload.add(pay);
        }
        return new PayloadWithCollection<>(200, "Успешно", payload);
    }

    public Payload register(PartRegDTO partRegDTO) {
        try {
            participantRegistrationRepository.addRegistration(
                    partRegDTO.getPersonId(),
                    partRegDTO.getRegId(),
                    LocalDateTime.now()
            );
        } catch (DataAccessException e) {
            return new BasePayload(400, "Ошибка при добавлении записи");
        } catch (DataIntegrityViolationException e) {
            return new BasePayload(400, "Некорректные данные");
        }
        return new BasePayload(200, "Успешно");
    }
}
