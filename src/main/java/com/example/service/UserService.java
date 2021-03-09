package com.example.service;


import com.example.dao.UserDao;
import com.example.enums.COUNTRY;
import com.example.enums.ROLE;
import com.example.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserDao userDao;

    public void save(User user) {
        userDao.save(user);
    }

    public List<User> findAll() {
        return userDao.findAllByAndRoleIsNotAndStatusIsGreaterThan(ROLE.ADMIN.name(), -1);
    }

    public List<User> findAllByCountry(String country) {
        return userDao.findAllByCountryAndStatusIsGreaterThanAndRoleIsNot(COUNTRY.valueOf(country), -1, ROLE.ADMIN.name());
    }

    public List<User> findByNameContainsIgnoreCase(String txt) {
        return userDao.findAllByFullNameContainsIgnoreCaseAndStatusIsGreaterThanAndRoleIsNot(txt, -1, ROLE.ADMIN.name());
    }

    public User findById(Long id) {
        return userDao.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User: %s isn't found in our DB with that id", id)
                ));
    }

    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public void delete(Long id) {

    userDao.delete(id);

    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        return userDao.findByEmail(mail)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User: %s isn't found in our DB with that mail", mail)
                ));
    }

    public void update(User user) {
        userDao.update(user);
    }
}
