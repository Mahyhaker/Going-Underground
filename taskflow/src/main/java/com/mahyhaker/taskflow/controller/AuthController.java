package com.mahyhaker.taskflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahyhaker.taskflow.dto.AuthRequestDTO;
import com.mahyhaker.taskflow.dto.AuthResponseDTO;
import com.mahyhaker.taskflow.dto.RegisterAdminRequestDTO;
import com.mahyhaker.taskflow.dto.RegisterRequestDTO;
import com.mahyhaker.taskflow.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registrar(@Valid @RequestBody RegisterRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(dto));
    }

    @PostMapping("/register-admin")
    public ResponseEntity<AuthResponseDTO> registrarAdmin(@Valid @RequestBody RegisterAdminRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarAdmin(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO dto) {
        return ResponseEntity.ok(service.login(dto));
    }
}