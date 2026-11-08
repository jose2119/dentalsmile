package com.dentalsmile.dentalsmile.service;

import com.dentalsmile.dentalsmile.dto.request.LoginRequest;
import com.dentalsmile.dentalsmile.dto.request.RegisterRequest;
import com.dentalsmile.dentalsmile.entity.Usuario;
import com.dentalsmile.dentalsmile.exception.BusinessException;
import com.dentalsmile.dentalsmile.repository.UsuarioRepository;
import com.dentalsmile.dentalsmile.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider      tokenProvider;
    private final PasswordEncoder       encoder;
    private final UsuarioRepository     usuarioRepository;

    public AuthService(AuthenticationManager authManager,
                       JwtTokenProvider tokenProvider,
                       PasswordEncoder encoder,
                       UsuarioRepository usuarioRepository) {
        this.authManager       = authManager;
        this.tokenProvider     = tokenProvider;
        this.encoder           = encoder;
        this.usuarioRepository = usuarioRepository;
    }

    public Map<String, Object> login(LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        String token = tokenProvider.generarToken(auth);
        Usuario usuario = (Usuario) auth.getPrincipal();
        return Map.of(
                "token", token,
                "tipo", "Bearer",
                "username", usuario.getUsername(),
                "rol", usuario.getRol(),
                "nombreCompleto", usuario.getNombreCompleto() != null ? usuario.getNombreCompleto() : ""
        );
    }

    @Transactional
    public Map<String, String> register(RegisterRequest req) {
        if (usuarioRepository.existsByUsername(req.getUsername())) {
            throw new BusinessException("El username '" + req.getUsername() + "' ya está en uso.");
        }
        String rol = (req.getRol() != null && !req.getRol().isBlank()) ? req.getRol() : "RECEPCIONISTA";
        Usuario u = Usuario.builder()
                .username(req.getUsername())
                .passwordHash(encoder.encode(req.getPassword()))
                .nombreCompleto(req.getNombreCompleto())
                .correo(req.getCorreo())
                .rol(rol)
                .activo(true)
                .build();
        usuarioRepository.save(u);
        return Map.of("mensaje", "Usuario registrado correctamente.", "username", u.getUsername());
    }
}

