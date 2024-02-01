package com.coursework.coursework.service.impl;

import com.coursework.coursework.dto.EventIdDTO;
import com.coursework.coursework.dto.PartPurchaseDTO;
import com.coursework.coursework.dto.SalesIdDTO;
import com.coursework.coursework.model.participant.entity.ParticipantPurchase;
import com.coursework.coursework.model.participant.payload.ParticipantPurchasePayload;
import com.coursework.coursework.model.payload.BasePayload;
import com.coursework.coursework.model.payload.Payload;
import com.coursework.coursework.model.payload.PayloadWithCollection;
import com.coursework.coursework.model.person.entity.Person;
import com.coursework.coursework.model.purchase.entity.EventTicketSales;
import com.coursework.coursework.model.user.entity.User;
import com.coursework.coursework.repository.*;
import com.coursework.coursework.repository.helper.Pair;
import com.coursework.coursework.repository.helper.QueryFetchHelper;
import com.coursework.coursework.utils.ParticipantPurchaseConverter;
import org.jooq.exception.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class ParticipantPurchaseService {
    private final ParticipantPurchaseRepository participantPurchaseRepository;
    private final ParticipantRepository participantRepository;
    private final EventTicketSalesRepository eventTicketSalesRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;

    public ParticipantPurchaseService(ParticipantPurchaseRepository participantPurchaseRepository,
                                      ParticipantRepository participantRepository, EventTicketSalesRepository eventTicketSalesRepository, PersonRepository personRepository, UserRepository userRepository) {
        this.participantPurchaseRepository = participantPurchaseRepository;
        this.participantRepository = participantRepository;
        this.eventTicketSalesRepository = eventTicketSalesRepository;
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    public Payload buyTicket(PartPurchaseDTO partPurchaseDTO) {
        EventTicketSales process = eventTicketSalesRepository.findById(partPurchaseDTO.getSalesId());
        if (process == null) {
            return new BasePayload(400, "Процесс не найден");
        }
        Integer newParticipantPurchaseId;
        try {
            newParticipantPurchaseId = participantPurchaseRepository.addPurchase(
                    partPurchaseDTO.getSalesId(),
                    partPurchaseDTO.getPartRegId(),
                    partPurchaseDTO.getTicketAmount()
            );
            participantRepository.add(
                    newParticipantPurchaseId,
                    partPurchaseDTO.getPersonId(),
                    partPurchaseDTO.getPassport()
            );
            User user = userRepository.findByPerson(partPurchaseDTO.getPersonId());
            userRepository.updateRole(user);
        } catch (DataAccessException e) {
            return new BasePayload(400, "Ошибка при добавлении записи");
        } catch (DataIntegrityViolationException e) {
            return new BasePayload(400, "Некорректные записи");
        }
        return new BasePayload(200, "Успешно");
    }

    public Payload getBoughtTicket(Integer personId) {
        return getPersonTicketsWrapper(personId, participantPurchaseRepository::findByPersonId);
    }

    public Payload getPersonTicketsWrapper(Integer personId,
                                                 Function<Integer, List<ParticipantPurchase>> sqlRequest) {
        if (personId == null) {
            return new BasePayload(400, "id человека не может быть null");
        }
        Person person = personRepository.find(personId);
        if (person == null) {
            return new BasePayload(400, "Человек не найден");
        }

        QueryFetchHelper<Integer, List<ParticipantPurchase>> helper = new QueryFetchHelper<>(
                personId, sqlRequest
        );
        Pair<List<ParticipantPurchase>, String> pair = helper.fetch();
        List<ParticipantPurchase> records = pair.getFirst();
        if (records == null) {
            return new BasePayload(400, pair.getSecond());
        }
        List<ParticipantPurchasePayload> payload = new ArrayList<>();
        for (ParticipantPurchase participantPurchase : records) {
            ParticipantPurchasePayload pay = ParticipantPurchaseConverter.convert(participantPurchase,
                    participantPurchase.getParticipantRegistration(),
                    participantPurchase.getParticipantRegistration().getEventRegistration().getTicket());
            payload.add(pay);
        }
        return new PayloadWithCollection<>(200, "Успешно", payload);
    }

    public Payload getAllParticipants(EventIdDTO eventIdDTO) {
        return geAllParticipantsWrapper(eventIdDTO.getEventId(), participantPurchaseRepository::findAllParticipantsPerEvent);
    }

    public Payload geAllParticipantsWrapper(Integer eventId,
                                           Function<Integer, List<ParticipantPurchase>> sqlRequest) {
        if (eventId == null) {
            return new BasePayload(400, "id event не может быть null");
        }

        QueryFetchHelper<Integer, List<ParticipantPurchase>> helper = new QueryFetchHelper<>(
                eventId, sqlRequest
        );
        Pair<List<ParticipantPurchase>, String> pair = helper.fetch();
        List<ParticipantPurchase> records = pair.getFirst();
        if (records == null) {
            return new BasePayload(400, pair.getSecond());
        }
        List<ParticipantPurchasePayload> payload = new ArrayList<>();
        for (ParticipantPurchase participantPurchase : records) {
            ParticipantPurchasePayload pay = ParticipantPurchaseConverter.convert(participantPurchase,
                    participantPurchase.getParticipantRegistration(),
                    participantPurchase.getParticipantRegistration().getEventRegistration().getTicket());
            payload.add(pay);
        }
        return new PayloadWithCollection<>(200, "Успешно", payload);
    }

    public Payload getParticipantsPerSale(SalesIdDTO salesIdDTO) {
        return getParticipantsPerSaleWrapper(salesIdDTO.getSalesId(), participantPurchaseRepository::findParticipantsPerSale);
    }

    public Payload getParticipantsPerSaleWrapper(Integer salesId,
                                            Function<Integer, List<ParticipantPurchase>> sqlRequest) {
        if (salesId == null) {
            return new BasePayload(400, "id sales не может быть null");
        }

        QueryFetchHelper<Integer, List<ParticipantPurchase>> helper = new QueryFetchHelper<>(
                salesId, sqlRequest
        );
        Pair<List<ParticipantPurchase>, String> pair = helper.fetch();
        List<ParticipantPurchase> records = pair.getFirst();
        if (records == null) {
            return new BasePayload(400, pair.getSecond());
        }
        List<ParticipantPurchasePayload> payload = new ArrayList<>();
        for (ParticipantPurchase participantPurchase : records) {
            ParticipantPurchasePayload pay = ParticipantPurchaseConverter.convert(participantPurchase,
                    participantPurchase.getParticipantRegistration(),
                    participantPurchase.getParticipantRegistration().getEventRegistration().getTicket());
            payload.add(pay);
        }
        return new PayloadWithCollection<>(200, "Успешно", payload);
    }
}
