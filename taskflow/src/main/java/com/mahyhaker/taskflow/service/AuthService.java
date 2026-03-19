package com.mahyhaker.taskflow.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mahyhaker.taskflow.dto.AuthRequestDTO;
import com.mahyhaker.taskflow.dto.AuthResponseDTO;
import com.mahyhaker.taskflow.dto.RegisterAdminRequestDTO;
import com.mahyhaker.taskflow.dto.RegisterRequestDTO;
import com.mahyhaker.taskflow.entity.Usuario;
import com.mahyhaker.taskflow.enums.RoleUsuario;
import com.mahyhaker.taskflow.repository.UsuarioRepository;
import com.mahyhaker.taskflow.security.JwtService;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDTO registrar(RegisterRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário com esse email.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setRole(RoleUsuario.ROLE_USER);

        Usuario salvo = usuarioRepository.save(usuario);
        String token = jwtService.generateToken(salvo);

        return new AuthResponseDTO(token, "Bearer");
    }

    public AuthResponseDTO registrarAdmin(RegisterAdminRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário com esse email.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setRole(RoleUsuario.ROLE_ADMIN);

        Usuario salvo = usuarioRepository.save(usuario);
        String token = jwtService.generateToken(salvo);

        return new AuthResponseDTO(token, "Bearer");
    }

    public AuthResponseDTO login(AuthRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
        );

        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        String token = jwtService.generateToken(usuario);

        return new AuthResponseDTO(token, "Bearer");
    }
}