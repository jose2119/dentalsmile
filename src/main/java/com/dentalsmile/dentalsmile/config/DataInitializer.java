package com.dentalsmile.dentalsmile.config;

import com.dentalsmile.dentalsmile.entity.Usuario;
import com.dentalsmile.dentalsmile.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Crea el usuario administrador por defecto si la tabla está vacía.
 * Cambia la contraseña desde el endpoint /api/auth/register en producción.
 */
@Component
public class DataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UsuarioRepository repo;
    private final PasswordEncoder   encoder;

    public DataInitializer(UsuarioRepository repo, PasswordEncoder encoder) {
        this.repo    = repo;
        this.encoder = encoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (repo.count() == 0) {
            Usuario admin = Usuario.builder()
                    .username("admin")
                    .passwordHash(encoder.encode("admin123"))
                    .nombreCompleto("Administrador HappySmile")
                    .correo("admin@happysmile.pe")
                    .rol("ADMIN")
                    .activo(true)
                    .build();
            repo.save(admin);
            log.warn(">>> Usuario admin creado con contraseña por defecto. " +
                     "CAMBIA LA CONTRASEÑA EN PRODUCCIÓN. <<<");
        }
    }
}

