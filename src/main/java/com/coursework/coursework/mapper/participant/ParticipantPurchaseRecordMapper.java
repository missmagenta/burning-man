package com.coursework.coursework.mapper.participant;

import com.coursework.coursework.model.participant.entity.ParticipantPurchase;
import com.coursework.coursework.model.participant.entity.ParticipantRegistration;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.coursework.coursework.utils.TableAliases.PARTICIPANT_PURCHASE_TABLE;

@Component
public class ParticipantPurchaseRecordMapper {
    private final ParticipantRegistrationRecordMapper participantRegistrationMapper;

    public ParticipantPurchaseRecordMapper(ParticipantRegistrationRecordMapper participantRegistrationMapper) {
        this.participantRegistrationMapper = participantRegistrationMapper;
    }

    public ParticipantPurchase map(Record record) {
        Integer id = record.get(PARTICIPANT_PURCHASE_TABLE.ID);
        Integer salesId = record.get(PARTICIPANT_PURCHASE_TABLE.PURCHASE_ID);
        Integer participantRegistrationId = record.get(PARTICIPANT_PURCHASE_TABLE.PARTICIPANT_REGISTRATION_ID);
        LocalDateTime purchaseDateTime = record.get(PARTICIPANT_PURCHASE_TABLE.PURCHASE_DATE);
        Integer ticketsAmountPurchased = record.get(PARTICIPANT_PURCHASE_TABLE.TICKETS_AMOUNT_PURCHASED);

        ParticipantRegistration participantRegistration = participantRegistrationMapper.map(record);

        ParticipantPurchase participantPurchase = new ParticipantPurchase();
        participantPurchase.setId(id);
        participantPurchase.setParticipantRegistrationId(participantRegistrationId);
        participantPurchase.setEventTicketSalesId(salesId);
        participantPurchase.setPurchaseDateTime(purchaseDateTime);
        participantPurchase.setTicketsAmountPurchased(ticketsAmountPurchased);
        participantPurchase.setParticipantRegistration(participantRegistration);

        return participantPurchase;
    }
}
