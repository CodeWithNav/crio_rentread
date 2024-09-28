package com.nav.rentread.service;

import com.nav.rentread.dto.UserRegisterDto;
import com.nav.rentread.entities.User;
import com.nav.rentread.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User register(UserRegisterDto userRegisterDto) {
        User user = User
                .builder()
                .firstName(userRegisterDto.getFirstName())
                .lastName(userRegisterDto.getFirstName())
                .email(userRegisterDto.getEmail())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .role(userRegisterDto.getRole())
                .build();

        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }





}

