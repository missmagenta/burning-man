package com.coursework.coursework.mapper.user;

import com.coursework.coursework.model.user.entity.User;
import com.coursework.coursework.model.user.entity.UserRole;

import com.coursework.coursework.models.tables.Users;
import com.coursework.coursework.models.tables.records.UsersRecord;

import org.hibernate.MappingException;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserRecordUnmapper {
    @Autowired
    private final DSLContext dslContext;

    public UserRecordUnmapper(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public UsersRecord unmap(User user) throws MappingException {
        UsersRecord record = dslContext.newRecord(Users.USERS, user);
        record.setRole(convert(user.getRole()));
        return record;
    }

    private com.coursework.coursework.models.enums.UserRole convert(UserRole role) {
        return com.coursework.coursework.models.enums.UserRole.valueOf(role.name().toUpperCase());
    }
}
