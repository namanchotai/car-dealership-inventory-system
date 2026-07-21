package com.incubyte.car_dealership.service;

import com.incubyte.car_dealership.dto.RegisterRequest;
import com.incubyte.car_dealership.entity.User;
import com.incubyte.car_dealership.exception.UserAlreadyExistsException;
import com.incubyte.car_dealership.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service responsible for handling authentication-related business logic.
 *
 * <p>Currently, this service supports user registration by:
 * <ul>
 *     <li>Checking if the email is already registered.</li>
 *     <li>Encrypting the user's password before storage.</li>
 *     <li>Creating and saving a new {@link User}.</li>
 * </ul>
 *
 * @author Naman Chotai
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user in the system.
     *
     * <p>The registration process performs the following steps:
     * <ol>
     *     <li>Checks whether the provided email already exists.</li>
     *     <li>Encodes the user's password using the configured
     *         {@link PasswordEncoder}.</li>
     *     <li>Creates a new {@link User} entity.</li>
     *     <li>Saves the user to the database.</li>
     * </ol>
     *
     * @param request the registration request containing the username,
     *                email, and password
     * @throws UserAlreadyExistsException if the email is already registered
     */
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("Email already registered!");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userRepository.save(user);
    }
}