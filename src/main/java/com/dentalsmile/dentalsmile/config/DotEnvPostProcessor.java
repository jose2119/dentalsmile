package com.dentalsmile.dentalsmile.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Carga el archivo .env del directorio de trabajo al entorno de Spring Boot.
 * Solo actúa si el archivo existe; en Render las variables llegan como
 * variables de entorno del sistema operativo y este procesador las omite.
 * <p>
 * Registrado en META-INF/spring/
 * org.springframework.boot.env.EnvironmentPostProcessor
 */
public class DotEnvPostProcessor implements EnvironmentPostProcessor {

    private static final String ENV_FILE = ".env";
    private static final String SOURCE_NAME = "dotEnvFile";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment,
                                       SpringApplication application) {
        Path envPath = Path.of(System.getProperty("user.dir"), ENV_FILE);
        if (!envPath.toFile().exists()) {
            return;
        }

        Map<String, Object> props = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(envPath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.strip();
                if (line.isBlank() || line.startsWith("#")) continue;
                int idx = line.indexOf('=');
                if (idx < 1) continue;
                String key   = line.substring(0, idx).strip();
                String value = line.substring(idx + 1).strip();
                // No sobreescribe variables del sistema operativo (Render, CI, etc.)
                if (!environment.containsProperty(key)) {
                    props.put(key, value);
                }
            }
        } catch (IOException e) {
            // Si no se puede leer, se ignora silenciosamente
        }

        if (!props.isEmpty()) {
            environment.getPropertySources()
                    .addLast(new MapPropertySource(SOURCE_NAME, props));
        }
    }
}

