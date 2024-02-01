package com.coursework.coursework.mapper.person;

import com.coursework.coursework.model.person.entity.Gender;
import com.coursework.coursework.model.person.entity.Person;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import static com.coursework.coursework.utils.TableAliases.PERSON_TABLE;

@Component
public class PersonRecordMapper {

    public Person mapPerson(Record record) {
        return mapPerson(record, PERSON_TABLE);
    }

    public Person mapPerson(Record record, com.coursework.coursework.models.tables.Person alias) {
        Person person = new Person();
        person.setId(record.get(alias.ID));
        person.setName(record.get(alias.NAME));
        person.setSurname(record.get(alias.SURNAME));
        person.setEmail(record.get(alias.EMAIL));
        person.setContactNumber(record.get(alias.CONTACT_NUMBER));
        person.setBirthDate(record.get(alias.BIRTH_DATE));
        person.setGender(convertGender(record.get(alias.GENDER)));
        person.setDeathDate(record.get(alias.DEATH_DATE));
        return person;
    }


    private Gender convertGender(com.coursework.coursework.models.enums.GenderEnum modelName) {
        return Gender.valueOf(modelName.name());
    }
}
