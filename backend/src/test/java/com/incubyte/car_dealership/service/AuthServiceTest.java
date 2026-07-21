package com.incubyte.car_dealership.service;

import com.incubyte.car_dealership.dto.RegisterRequest;
import com.incubyte.car_dealership.entity.User;
import com.incubyte.car_dealership.exception.UserAlreadyExistsException;
import com.incubyte.car_dealership.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AuthServiceTest {
    @Autowired private AuthService authService;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Test
    void shouldRegisterNewUserSuccessfully() {

        RegisterRequest request = new RegisterRequest(
                "john",
                "john@gmail.com",
                "password123"
        );

        authService.register(request);

        User saved = userRepository
                .findByEmail("john@gmail.com")
                .orElseThrow();

        assertEquals("john", saved.getUsername());
        assertEquals("john@gmail.com", saved.getEmail());
        assertTrue(passwordEncoder.matches("password123", saved.getPassword()));
    }

    @Test
    void shouldThrowWhenEmailAlreadyRegistered() {

        RegisterRequest request = new RegisterRequest(
                "john",
                "john@gmail.com",
                "password123"
        );

        authService.register(request);

        assertThrows(
                UserAlreadyExistsException.class,
                () -> authService.register(request)
        );
    }
}
