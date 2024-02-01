package com.coursework.coursework.repository;

import com.coursework.coursework.mapper.person.PersonRecordMapper;
import com.coursework.coursework.model.person.entity.Gender;
import com.coursework.coursework.model.person.entity.Person;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.coursework.coursework.utils.TableAliases.PERSON_TABLE;

@Repository
public class PersonRepository {
    private final DSLContext dslContext;
    private final PersonRecordMapper personRecordMapper;

    @Autowired
    public PersonRepository(DSLContext dslContext, PersonRecordMapper personRecordMapper) {
        this.dslContext = dslContext;
        this.personRecordMapper = personRecordMapper;
    }

    @Transactional(readOnly = true)
    public Person find(Integer id) {
        return dslContext.selectFrom(PERSON_TABLE)
                .where(PERSON_TABLE.ID.eq(id))
                .fetchOptional()
                .map(personRecordMapper::mapPerson)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public Person findByCondition(Condition condition) {
        return dslContext.selectFrom(PERSON_TABLE)
                .where(condition)
                .fetchOptional()
                .map(personRecordMapper::mapPerson)
                .orElse(null);
    }

    public static Condition allFieldsExceptId(
            String name,
            String surname,
            String email,
            String phoneNumber,
            Gender gender,
            LocalDate birthDate
    ) {
        com.coursework.coursework.models.enums.GenderEnum genderModels =
                com.coursework.coursework.models.enums.GenderEnum.valueOf(gender.toString());
        return DSL.condition(PERSON_TABLE.NAME.eq(name))
                .and(PERSON_TABLE.SURNAME.eq(surname))
                .and(PERSON_TABLE.EMAIL.eq(email))
                .and(PERSON_TABLE.CONTACT_NUMBER.eq(phoneNumber))
                .and(PERSON_TABLE.GENDER.eq(genderModels))
                .and(PERSON_TABLE.BIRTH_DATE.eq(birthDate));
    }

    @Transactional
    public Person insert(Person person) {
        Integer maxId = dslContext.select(DSL.max(PERSON_TABLE.ID))
                .from(PERSON_TABLE)
                .fetchOne(0, Integer.class);

        Integer nextId = maxId != null ? maxId + 1 : 1;

        person.setId(nextId);
        return dslContext.insertInto(PERSON_TABLE)
                .set(dslContext.newRecord(PERSON_TABLE, person))
                .returning()
                .fetchOptional()
                .map(personRecordMapper::mapPerson)
                .orElse(null);

    }

}
