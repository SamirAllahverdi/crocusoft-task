package com.example.model;


import com.example.enums.COUNTRY;
import com.example.enums.ROLE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;


@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String email;

    @Size(min = 3, message = "Password must be at least three characters long")
    private String password;

    private String fullName;

    private int status;

    @Enumerated(EnumType.STRING)
    private COUNTRY country;

    private String role = ROLE.USER.name();

    public User(long id, String email, String password, String fullName, COUNTRY country, int status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.status = status;
        this.country = country;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>() {{
            add((GrantedAuthority) () -> String.format("ROLE_%s", role));
        }};
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
