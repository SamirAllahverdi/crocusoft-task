package com.example.config;

import com.example.enums.ROLE;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .authorizeRequests()
                .antMatchers("/main-page-user").hasRole(ROLE.USER.name())
                .antMatchers("/main-page-admin").hasRole(ROLE.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/default");
    }
}
