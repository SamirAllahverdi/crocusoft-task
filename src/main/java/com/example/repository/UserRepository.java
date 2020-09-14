package com.example.repository;

import com.example.enums.COUNTRY;
import com.example.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByNameContainsIgnoreCaseAndStatusIsGreaterThanAndRoleIsNot(String txt, int status, String  role);

    Optional<User> findByEmail(String email);

    List<User> findAllByCountryAndStatusIsGreaterThanAndRoleIsNot( COUNTRY country, int status,String role);

    List<User> findAllByAndRoleIsNotAndStatusIsGreaterThan(String role, int status);

}
