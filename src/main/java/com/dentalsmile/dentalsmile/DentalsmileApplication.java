package com.dentalsmile.dentalsmile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;

@SpringBootApplication
public class DentalsmileApplication {

    public static void main(String[] args) {
        cargarVariablesEnv();
        SpringApplication.run(DentalsmileApplication.class, args);
    }

    /**
     * Lee el archivo .env si existe en el directorio de trabajo o directorio raíz
     * y carga las propiedades en System Properties para Spring Boot.
     */
    private static void cargarVariablesEnv() {
        File envFile = new File(".env");
        if (!envFile.exists()) {
            envFile = Path.of(System.getProperty("user.dir"), ".env").toFile();
        }
        if (!envFile.exists()) {
            envFile = Path.of(System.getProperty("user.dir"), "..", ".env").toFile();
        }

        if (envFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(envFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) {
                        continue;
                    }
                    int idx = line.indexOf('=');
                    if (idx > 0) {
                        String key = line.substring(0, idx).trim();
                        String value = line.substring(idx + 1).trim();
                        // No sobrescribir variables de entorno fijadas por el sistema operativo
                        if (System.getenv(key) == null && System.getProperty(key) == null) {
                            System.setProperty(key, value);
                        }
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }
}
