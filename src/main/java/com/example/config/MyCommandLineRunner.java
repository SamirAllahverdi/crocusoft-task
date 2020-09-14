package com.example.config;

import com.example.enums.COUNTRY;
import com.example.enums.ROLE;
import com.example.model.User;
import com.example.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
public class MyCommandLineRunner {

    private final UserService service;
    private final PasswordEncoder encoder;

    @Bean
    public CommandLineRunner autoRun() {
        return args -> {
            service.save(new User(1,"mehi@gmail.com", encoder.encode("123"), "Mehdi", "Eliyev",1, COUNTRY.AZERBAIJAN, ROLE.ADMIN.name()));

            service.save(new User(2,"qezi@gmail.com",encoder.encode("123"), "Qezenfer", "Qasimov", COUNTRY.AZERBAIJAN ,1));
            service.save(new User(3,"eli@gmail.com",encoder.encode("123"), "Eliabbas", "Mecidov", COUNTRY.TURKEY, 1));
            service.save(new User(4,"hesen@gmail.com",encoder.encode("123"),  "Heseneli", "Haqverdiyev", COUNTRY.PAKISTAN, 0));
        };
    }

}
