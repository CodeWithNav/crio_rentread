package com.nav.rentread.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role == Role.ADMIN){
            return  List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        }
        return  List.of(
                new SimpleGrantedAuthority("ROLE_USER")
        );
    }

    @Override
    public String getUsername() {
        return email;
    }

    public enum Role {
        USER,
        ADMIN
    }
}

