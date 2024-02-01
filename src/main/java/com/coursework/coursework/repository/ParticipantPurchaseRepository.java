package com.coursework.coursework.repository;

import com.coursework.coursework.mapper.participant.ParticipantPurchaseRecordMapper;
import com.coursework.coursework.model.participant.entity.ParticipantPurchase;
import com.coursework.coursework.models.routines.AddParticipantPurchase;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.coursework.coursework.utils.TableAliases.*;

@Repository
public class ParticipantPurchaseRepository {
    private final DSLContext dslContext;
    private final ParticipantPurchaseRecordMapper participantPurchaseMapper;

    @Autowired
    public ParticipantPurchaseRepository(DSLContext dslContext, ParticipantPurchaseRecordMapper participantPurchaseMapper) {
        this.dslContext = dslContext;
        this.participantPurchaseMapper = participantPurchaseMapper;
    }

    @Transactional
    public Integer addPurchase(Integer salesId,
                          Integer participantRegId,
                          Integer ticketsAmount) {
        AddParticipantPurchase addParticipantPurchase = new AddParticipantPurchase();
        addParticipantPurchase.setCurSalesId(salesId);
        addParticipantPurchase.setCurPartRegId(participantRegId);
        addParticipantPurchase.setCurTicketsAmount(ticketsAmount);
        addParticipantPurchase.execute(dslContext.configuration());
        return addParticipantPurchase.getReturnValue();
    }

    @Transactional(readOnly = true)
    public List<ParticipantPurchase> findByPersonId(Integer personId) {
        return dslContext.select()
                .from(PARTICIPANT_PURCHASE_TABLE)
                .join(PARTICIPANT_REGISTRATION_TABLE)
                .on(PARTICIPANT_PURCHASE_TABLE.PARTICIPANT_REGISTRATION_ID.eq(PARTICIPANT_REGISTRATION_TABLE.ID))
                .join(PERSON_TABLE)
                .on(PERSON_TABLE.ID.eq(PARTICIPANT_REGISTRATION_TABLE.PERSON_ID))
                .join(EVENT_REGISTRATION_TABLE)
                .on(EVENT_REGISTRATION_TABLE.ID.eq(PARTICIPANT_REGISTRATION_TABLE.REGISTRATION_ID))
                .join(TICKET_TABLE)
                .on(TICKET_TABLE.ID.eq(EVENT_REGISTRATION_TABLE.TICKET_TYPE_ID))
                .innerJoin(EVENT_MANAGEMENT_TABLE)
                .on(EVENT_MANAGEMENT_TABLE.ID.eq(EVENT_REGISTRATION_TABLE.ORG_MANAGES_EVENT_ID))
                .join(EVENT_TABLE)
                .on(EVENT_TABLE.ID.eq(EVENT_MANAGEMENT_TABLE.EVENT_ID))
                .join(ORGANIZER_TABLE)
                .on(ORGANIZER_TABLE.ID.eq(EVENT_MANAGEMENT_TABLE.ORGANIZER_ID))
                .join(ROLE_TABLE)
                .on((ROLE_TABLE.ID.eq(ORGANIZER_TABLE.ROLE_ID)))
                .where(PARTICIPANT_REGISTRATION_TABLE.PERSON_ID.eq(personId))
                .fetch()
                .map(participantPurchaseMapper::map);
    }

    @Transactional(readOnly = true)
    public List<ParticipantPurchase> findAllParticipantsPerEvent(Integer eventId) {
        return dslContext.select()
                .from(PARTICIPANT_PURCHASE_TABLE)
                .join(PARTICIPANT_REGISTRATION_TABLE)
                .on(PARTICIPANT_PURCHASE_TABLE.PARTICIPANT_REGISTRATION_ID.eq(PARTICIPANT_REGISTRATION_TABLE.ID))
                .join(PERSON_TABLE)
                .on(PERSON_TABLE.ID.eq(PARTICIPANT_REGISTRATION_TABLE.PERSON_ID))
                .join(EVENT_REGISTRATION_TABLE)
                .on(EVENT_REGISTRATION_TABLE.ID.eq(PARTICIPANT_REGISTRATION_TABLE.REGISTRATION_ID))
                .join(TICKET_TABLE)
                .on(TICKET_TABLE.ID.eq(EVENT_REGISTRATION_TABLE.TICKET_TYPE_ID))
                .innerJoin(EVENT_MANAGEMENT_TABLE)
                .on(EVENT_MANAGEMENT_TABLE.ID.eq(EVENT_REGISTRATION_TABLE.ORG_MANAGES_EVENT_ID))
                .join(EVENT_TABLE)
                .on(EVENT_TABLE.ID.eq(EVENT_MANAGEMENT_TABLE.EVENT_ID))
                .join(ORGANIZER_TABLE)
                .on(ORGANIZER_TABLE.ID.eq(EVENT_MANAGEMENT_TABLE.ORGANIZER_ID))
                .join(ROLE_TABLE)
                .on((ROLE_TABLE.ID.eq(ORGANIZER_TABLE.ROLE_ID)))
                .where(EVENT_TABLE.ID.eq(eventId))
                .fetch()
                .map(participantPurchaseMapper::map);
    }

    @Transactional(readOnly = true)
    public List<ParticipantPurchase> findParticipantsPerSale(Integer salesId) {
        return dslContext.select()
                .from(PARTICIPANT_PURCHASE_TABLE)
                .join(EVENT_SALES_TABLE)
                .on(EVENT_SALES_TABLE.ID.eq(PARTICIPANT_PURCHASE_TABLE.PURCHASE_ID))
                .join(PARTICIPANT_REGISTRATION_TABLE)
                .on(PARTICIPANT_PURCHASE_TABLE.PARTICIPANT_REGISTRATION_ID.eq(PARTICIPANT_REGISTRATION_TABLE.ID))
                .join(PERSON_TABLE)
                .on(PERSON_TABLE.ID.eq(PARTICIPANT_REGISTRATION_TABLE.PERSON_ID))
                .join(EVENT_REGISTRATION_TABLE)
                .on(EVENT_REGISTRATION_TABLE.ID.eq(PARTICIPANT_REGISTRATION_TABLE.REGISTRATION_ID))
                .join(TICKET_TABLE)
                .on(TICKET_TABLE.ID.eq(EVENT_REGISTRATION_TABLE.TICKET_TYPE_ID))
                .innerJoin(EVENT_MANAGEMENT_TABLE)
                .on(EVENT_MANAGEMENT_TABLE.ID.eq(EVENT_REGISTRATION_TABLE.ORG_MANAGES_EVENT_ID))
                .join(EVENT_TABLE)
                .on(EVENT_TABLE.ID.eq(EVENT_MANAGEMENT_TABLE.EVENT_ID))
                .join(ORGANIZER_TABLE)
                .on(ORGANIZER_TABLE.ID.eq(EVENT_MANAGEMENT_TABLE.ORGANIZER_ID))
                .join(ROLE_TABLE)
                .on((ROLE_TABLE.ID.eq(ORGANIZER_TABLE.ROLE_ID)))
                .where(EVENT_SALES_TABLE.ID.eq(salesId))
                .fetch()
                .map(participantPurchaseMapper::map);
    }
}
