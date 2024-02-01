package com.coursework.coursework.mapper.user;

import com.coursework.coursework.model.user.entity.User;
import com.coursework.coursework.model.user.entity.UserRole;

import org.jooq.Record;
import org.springframework.stereotype.Component;

import static com.coursework.coursework.utils.TableAliases.USERS_TABLE;

@Component
public class UserRecordMapper {
    public User mapUser(Record record) {
        User user = new User();

        user.setId(record.get(USERS_TABLE.ID));
        user.setPersonId(record.get(USERS_TABLE.PERSON_ID));
        user.setUsername(record.get(USERS_TABLE.USERNAME));
        user.setPassword(record.get(USERS_TABLE.PASSWORD));
        user.setRole(convertRole(record.get(USERS_TABLE.ROLE)));

        return user;
    }

    private UserRole convertRole(com.coursework.coursework.models.enums.UserRole role) {
        return UserRole.valueOf(role.name().toUpperCase());
    }
}
