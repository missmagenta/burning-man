package com.coursework.coursework.service.impl;

import com.coursework.coursework.dto.EventSalesDTO;
import com.coursework.coursework.dto.SalesIdDTO;
import com.coursework.coursework.model.payload.*;
import com.coursework.coursework.model.purchase.entity.EventTicketSales;
import com.coursework.coursework.model.purchase.payload.EventSalesPayload;
import com.coursework.coursework.repository.EventTicketSalesRepository;
import com.coursework.coursework.repository.helper.Pair;
import com.coursework.coursework.repository.helper.QueryFetchHelper;
import com.coursework.coursework.utils.EventSalesConverter;
import org.jooq.exception.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventTicketSalesService {
    private final EventTicketSalesRepository eventTicketSalesRepository;

    public EventTicketSalesService(EventTicketSalesRepository eventTicketSalesRepository) {
        this.eventTicketSalesRepository = eventTicketSalesRepository;
    }

    public Payload startProcess(EventSalesDTO eventSalesDTO) {
        EventTicketSales process = eventTicketSalesRepository.findByRegistrationId(eventSalesDTO.getRegId());
        if (process != null) {
            return new BasePayload(400, "Продажи данного типа билетов уже начаты");
        }
        Integer newId;
        try {
             newId = eventTicketSalesRepository.startSales(eventSalesDTO.getRegId(), eventSalesDTO.getTicketsAvailable());
        } catch (DataAccessException e) {
            return new BasePayload(400, "Процесс сбора доносов завершен");
        } catch (DataIntegrityViolationException e) {
            return new BasePayload(400, "Некорректные данные");
        }

        return new PayloadWithInteger(200, "Начался процесс продажи билетов", newId);
    }

    public Payload finishProcess(SalesIdDTO salesIdDTO) {
        EventTicketSales process = eventTicketSalesRepository.findById(salesIdDTO.getSalesId());
        if (process == null) {
            return new BasePayload(400, "Продажи не найдены");
        }
        QueryFetchHelper<Integer, Integer> helper = new QueryFetchHelper<>(
                salesIdDTO.getSalesId(), eventTicketSalesRepository::finishSales
        );
        Pair<Integer, String> pair = helper.fetch();
        if (pair.getFirst() == null) {
            return new BasePayload(400, pair.getSecond());
        }
        return new PayloadWithInteger(200, "Процесс продажи билетов завершен", pair.getFirst());
    }

    public Payload getAllSalesPerEvent(Integer eventId) {
        QueryFetchHelper<Integer, List<EventTicketSales>> helper = new QueryFetchHelper<>(
                eventId, eventTicketSalesRepository::findAllPerEvent
        );
        Pair<List<EventTicketSales>, String> pair = helper.fetch();
        List<EventTicketSales> records = pair.getFirst();
        if (records == null) {
            return new BasePayload(400, "Ошибка выполнения запроса");
        }
        List<EventSalesPayload> payload = records.stream()
                .map(EventSalesConverter::convertToPayload)
                .toList();
        return new PayloadWithCollection<>(200, payload);
    }


    public Payload getSalePerOrg(Integer curOrgId) {
        QueryFetchHelper<Integer, List<EventTicketSales>> helper = new QueryFetchHelper<>(
                curOrgId, eventTicketSalesRepository::findAllPerOrganizer
        );
        Pair<List<EventTicketSales>, String> pair = helper.fetch();
        List<EventTicketSales> records = pair.getFirst();

        List<EventSalesPayload> payload = new ArrayList<>();
        for (EventTicketSales eventTicketSales : records) {
            EventSalesPayload pay = EventSalesConverter.convertToPayload(
                    eventTicketSales);
            payload.add(pay);
        }
        return new PayloadWithCollection<>(200, payload);
    }
}
