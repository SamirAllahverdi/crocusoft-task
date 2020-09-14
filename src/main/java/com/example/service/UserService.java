package com.example.service;


import com.example.enums.COUNTRY;
import com.example.enums.ROLE;
import com.example.model.User;
import com.example.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;

    public void save(User user) {
        userRepo.save(user);
    }

    public List<User> findAll() {
        return userRepo.findAllByAndRoleIsNotAndStatusIsGreaterThan(ROLE.ADMIN.name(),-1);
    }

    public List<User> findAllByCountry(String country) {
        return userRepo.findAllByCountryAndStatusIsGreaterThanAndRoleIsNot(COUNTRY.valueOf(country),-1,ROLE.ADMIN.name());
    }

    public List<User> findByNameContainsIgnoreCase(String txt) {
        return userRepo.findByNameContainsIgnoreCaseAndStatusIsGreaterThanAndRoleIsNot(txt,-1, ROLE.ADMIN.name());
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User: %s isn't found in our DB with that id", id)
        ));
    }

    public void delete(Long id) {
        User user = findById(id);
        user.setStatus(-1);
        userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        return userRepo.findByEmail(mail)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User: %s isn't found in our DB with that mail", mail)
                ));
    }
}
