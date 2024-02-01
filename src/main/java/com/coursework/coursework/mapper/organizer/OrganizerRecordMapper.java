package com.coursework.coursework.mapper.organizer;

import com.coursework.coursework.mapper.person.PersonRecordMapper;
import com.coursework.coursework.model.organizer.entity.Organizer;
import com.coursework.coursework.model.organizer.entity.OrganizerRole;
import com.coursework.coursework.model.organizer.entity.Role;
import com.coursework.coursework.model.person.entity.Person;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import static com.coursework.coursework.utils.TableAliases.ROLE_TABLE;
import static com.coursework.coursework.utils.TableAliases.ORGANIZER_TABLE;

@Component
public class OrganizerRecordMapper {

    private final PersonRecordMapper personRecordMapper;

    public OrganizerRecordMapper(PersonRecordMapper personRecordMapper) {
        this.personRecordMapper = personRecordMapper;
    }

    public Role mapRole(Record record) {
        return mapRole(record, ROLE_TABLE);
    }

    public Role mapRole(Record record, com.coursework.coursework.models.tables.Role alias) {
        Role role = new Role();
        role.setId(record.get(alias.ID));
        role.setOrganizerRole(convertOrganizerRole(record.get(alias.NAME)));
        role.setDescription(record.get(alias.DESCRIPTION));

        return role;
    }

    private OrganizerRole convertOrganizerRole(com.coursework.coursework.models.enums.OrganizerRole alias) {
        return OrganizerRole.valueOf(alias.name().toUpperCase());
    }

    public Organizer mapOrganizer(Record record) {
        return mapOrganizer(record, ORGANIZER_TABLE);
    }

    public Organizer mapOrganizer(Record record, com.coursework.coursework.models.tables.Organizer alias) {
        Organizer organizer = new Organizer();
        organizer.setId(record.get(alias.ID));
        organizer.setPersonId(record.get(alias.PERSON_ID));
        organizer.setRoleId(record.get(alias.ROLE_ID));

        Person person = personRecordMapper.mapPerson(record);
        Role role = mapRole(record);

        organizer.setPerson(person);
        organizer.setRole(role);

        return organizer;
    }
}
