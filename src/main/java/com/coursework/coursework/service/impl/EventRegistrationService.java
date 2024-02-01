package com.coursework.coursework.service.impl;

import com.coursework.coursework.dto.EventRegistrationDTO;
import com.coursework.coursework.dto.RegIdDTO;
import com.coursework.coursework.model.event.entity.EventManagementProcess;
import com.coursework.coursework.model.payload.BasePayload;
import com.coursework.coursework.model.payload.Payload;
import com.coursework.coursework.model.payload.PayloadWithCollection;
import com.coursework.coursework.model.payload.PayloadWithInteger;
import com.coursework.coursework.model.purchase.entity.EventRegistration;
import com.coursework.coursework.model.purchase.payload.EventRegistrationPayload;
import com.coursework.coursework.repository.EventRegistrationRepository;
import com.coursework.coursework.repository.helper.Pair;
import com.coursework.coursework.repository.helper.QueryFetchHelper;
import com.coursework.coursework.utils.EventRegistrationConverter;
import org.jooq.exception.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventRegistrationService {
    private final EventRegistrationRepository eventRegistrationRepository;

    public EventRegistrationService(EventRegistrationRepository eventRegistrationRepository) {
        this.eventRegistrationRepository = eventRegistrationRepository;
    }

    public Payload startProcess(EventRegistrationDTO newEventRegistrationDTO) {

        try {
            eventRegistrationRepository.startRegistration(
                    newEventRegistrationDTO.getOmeId(),
                    newEventRegistrationDTO.getTicket_type(),
                    newEventRegistrationDTO.getPrice()
            );
        } catch (DataAccessException e) {
            return new BasePayload(400, "Процесс регистрации уже завершен");
        } catch (DataIntegrityViolationException e) {
            return new BasePayload(400, "Некорректные данные");
        }

        return new BasePayload(200, "Начался процесс регистрации на мероприятие");
    }

    public Payload finishProcess(RegIdDTO finishRegDTO) {
        QueryFetchHelper<Integer, Integer> helper = new QueryFetchHelper<>(
                finishRegDTO.getRegId(), eventRegistrationRepository::finishRegistration);
        Pair<Integer, String> pair = helper.fetch();
        if (pair.getFirst() == null) {
            return new BasePayload(400, pair.getSecond());
        }
        return new PayloadWithInteger(200, "Процесс регистрации на мероприятие завершен", pair.getFirst());
    }

    public Payload getAllRegistrationsPerEvent(Integer eventId) {
        QueryFetchHelper<Integer, List<EventRegistration>> helper = new QueryFetchHelper<>(
                eventId,
                eventRegistrationRepository::findAllPerEvent);

        Pair<List<EventRegistration>, String> pair = helper.fetch();
        List<EventRegistration> records = pair.getFirst();
        if (records == null) {
            return new BasePayload(400, "Ошибка выполнения запроса");
        }
        List<EventRegistrationPayload> payload = new ArrayList<>();
        for (EventRegistration eventRegistration : records) {
            EventRegistrationPayload pay = EventRegistrationConverter.convertToPayload(
                    eventRegistration,
                    eventRegistration.getEventManagementProcess());
            payload.add(pay);
        }
        return new PayloadWithCollection<>(200, payload);
    }

    public Payload getAllRegistrationsPerOrganizer(Integer curOrgId) {
        QueryFetchHelper<Integer, List<EventRegistration>> helper = new QueryFetchHelper<>(
                curOrgId,
                eventRegistrationRepository::findAllPerOrganizer);

        Pair<List<EventRegistration>, String> pair = helper.fetch();
        List<EventRegistration> records = pair.getFirst();
        if (records == null) {
            return new BasePayload(400, "Ошибка выполнения запроса");
        }

        QueryFetchHelper<Integer, EventManagementProcess> helper2 = new QueryFetchHelper<>(
                curOrgId,
                eventRegistrationRepository::findUpcomingOmeId);

        Pair<EventManagementProcess, String> pair2 = helper2.fetch();
        EventManagementProcess ome = pair2.getFirst();

        List<EventRegistrationPayload> payload = new ArrayList<>();
        EventRegistrationPayload omePay = EventRegistrationConverter.convertOme(ome);
        payload.add(omePay);

        for (EventRegistration eventRegistration : records) {
            EventRegistrationPayload pay = EventRegistrationConverter.convertToPayload(
                    eventRegistration,
                    eventRegistration.getEventManagementProcess());
            payload.add(pay);
        }
        return new PayloadWithCollection<>(200, payload);
    }



}
