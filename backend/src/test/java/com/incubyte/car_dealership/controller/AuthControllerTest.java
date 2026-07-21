package com.incubyte.car_dealership.controller;

import com.incubyte.car_dealership.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnCreatedOnSuccessfulRegistration() {

        RegisterRequest request = new RegisterRequest(
                "jane",
                "jane@gmail.com",
                "password123"
        );

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/auth/register",
                request,
                String.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}