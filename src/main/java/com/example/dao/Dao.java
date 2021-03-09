package com.example.dao;

import com.example.enums.COUNTRY;
import java.util.List;
import java.util.Optional;


public interface Dao<A> {

    Optional<A> findById(long id);

    List<A> findAllByFullNameContainsIgnoreCaseAndStatusIsGreaterThanAndRoleIsNot(String txt, int status, String role);

    Optional<A> findByEmail(String email);

    List<A> findAllByCountryAndStatusIsGreaterThanAndRoleIsNot(COUNTRY country, int status, String role);

    List<A> findAllByAndRoleIsNotAndStatusIsGreaterThan(String role, int status);

    void save(A a);

    void update(A a);

    void delete(Long id);
}
