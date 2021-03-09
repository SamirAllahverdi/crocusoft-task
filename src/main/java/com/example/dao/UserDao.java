package com.example.dao;

import com.example.enums.COUNTRY;
import com.example.model.User;
import lombok.extern.log4j.Log4j2;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.tej.JooQDemo.jooq.sample.model.Tables.*;
import static org.jooq.impl.DSL.row;


@Repository
@Log4j2
public class UserDao implements Dao<User> {


    private final DSLContext dsl;

    public UserDao(DSLContext dsl) {
        this.dsl = dsl;
    }


    @Override
    public Optional<User> findById(long id) {
        return dsl
                .selectFrom(USERS)
                .where(USERS.ID.equal(id))
                .fetchOptionalInto(User.class);
    }


    @Override
    public void save(User user) {

        // This if is for CommandLineData
        if (!findById(user.getId()).isPresent()) {
            dsl
                    .insertInto(USERS, USERS.ID, USERS.STATUS, USERS.COUNTRY, USERS.ROLE, USERS.EMAIL, USERS.FULL_NAME, USERS.PASSWORD)
                    .values(user.getId(), user.getStatus(), user.getCountry().toString(), user.getRole(), user.getEmail(), user.getFullName(), user.getPassword())
                    .execute();
        }

    }

    @Override
    public void update(User user) {
        dsl
                .update(USERS)
                .set(row(USERS.ID, USERS.STATUS, USERS.COUNTRY, USERS.ROLE, USERS.EMAIL, USERS.FULL_NAME, USERS.PASSWORD),
                        row(user.getId(), user.getStatus(), user.getCountry().toString(), user.getRole(), user.getEmail(), user.getFullName(), user.getPassword()))
                .where(USERS.ID.equal(user.getId()))
                .execute();
    }

    @Override
    public void delete(Long id) {
        dsl
                .update(USERS)
                .set(USERS.STATUS,-1)
                .where(USERS.ID.equal(id))
                .execute();
    }

    @Override
    public List<User> findAllByFullNameContainsIgnoreCaseAndStatusIsGreaterThanAndRoleIsNot(String txt, int status, String role) {
        return dsl
                .selectFrom(USERS)
                .where(USERS.FULL_NAME.contains(txt)
                        .and(USERS.STATUS.greaterThan(status))
                        .andNot(USERS.ROLE.equal(role)))
                .fetchInto(User.class);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return dsl
                .selectFrom(USERS)
                .where(USERS.EMAIL.equal(email))
                .fetchOptionalInto(User.class);
    }

    @Override
    public List<User> findAllByCountryAndStatusIsGreaterThanAndRoleIsNot(COUNTRY country, int status, String role) {
        return dsl
                .selectFrom(USERS)
                .where(USERS.COUNTRY.equal(country.toString())
                        .and(USERS.STATUS.greaterThan(status))
                        .andNot(USERS.ROLE.equal(role)))
                .fetchInto(User.class);
    }

    @Override
    public List<User> findAllByAndRoleIsNotAndStatusIsGreaterThan(String role, int status) {
        return dsl
                .selectFrom(USERS)
                .where(USERS.STATUS.greaterThan(status))
                .andNot(USERS.ROLE.equal(role))
                .fetchInto(User.class);
    }

}
