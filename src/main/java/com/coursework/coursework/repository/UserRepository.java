package com.coursework.coursework.repository;


import com.coursework.coursework.mapper.user.UserRecordMapper;
import com.coursework.coursework.mapper.user.UserRecordUnmapper;
import com.coursework.coursework.model.user.entity.User;
import com.coursework.coursework.models.enums.UserRole;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.coursework.coursework.utils.TableAliases.USERS_TABLE;

@Repository
public class UserRepository {
    private final DSLContext dslContext;
    private final UserRecordMapper userRecordMapper;
    private final UserRecordUnmapper userRecordUnmapper;

    @Autowired
    public UserRepository(DSLContext dslContext,
                          UserRecordMapper userRecordMapper,
                          UserRecordUnmapper userRecordUnmapper) {
        this.dslContext = dslContext;
        this.userRecordMapper = userRecordMapper;
        this.userRecordUnmapper = userRecordUnmapper;
    }

    @Transactional
    public User insert(User user) {
        return dslContext.insertInto(USERS_TABLE)
                .set(dslContext.newRecord(USERS_TABLE, user))
                .returning()
                .fetchOptional()
                .map(userRecordMapper::mapUser)
                .orElse(null);
    }

    @Transactional
    public User update(User user) {
        return dslContext.update(USERS_TABLE)
                .set(userRecordUnmapper.unmap(user))
                .where(USERS_TABLE.ID.eq(user.getId()))
                .returning()
                .fetchOptional()
                .map(userRecordMapper::mapUser)
                .orElse(null);
    }

    @Transactional
    public void updateRole(User user) {
        dslContext.update(USERS_TABLE)
                .set(USERS_TABLE.ROLE, UserRole.PARTICIPANT)
                .where(USERS_TABLE.PERSON_ID.eq(user.getPersonId()))
                .execute();
    }

    @Transactional(readOnly = true)
    public User find(Integer id) {
        return dslContext.selectFrom(USERS_TABLE)
                .where(USERS_TABLE.ID.eq(id))
                .fetchOptional()
                .map(userRecordMapper::mapUser)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public User findByPerson(Integer personId) {
        return dslContext.selectFrom(USERS_TABLE)
                .where(USERS_TABLE.PERSON_ID.eq(personId))
                .fetchOptional()
                .map(userRecordMapper::mapUser)
                .orElse(null);
    }

    @Transactional
    public Boolean delete(Integer id) {
        return dslContext.deleteFrom(USERS_TABLE)
                .where(USERS_TABLE.ID.eq(id))
                .execute() == 1;
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return dslContext.selectFrom(USERS_TABLE)
                .where(USERS_TABLE.USERNAME.eq(username))
                .fetchOptional()
                .map(userRecordMapper::mapUser)
                .orElse(null);
    }
}
