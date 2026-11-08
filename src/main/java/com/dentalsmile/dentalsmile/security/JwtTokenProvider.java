package com.dentalsmile.dentalsmile.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Genera, valida y analiza tokens JWT usando la biblioteca jjwt 0.12.x.
 */
@Component
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    // ── Generación ───────────────────────────────────────────

    public String generarToken(Authentication authentication) {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return construirToken(principal.getUsername());
    }

    public String generarToken(String username) {
        return construirToken(username);
    }

    private String construirToken(String subject) {
        Date now    = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(clave())
                .compact();
    }

    // ── Extracción ───────────────────────────────────────────

    public String getUsernameFromToken(String token) {
        return parsearClaims(token).getSubject();
    }

    // ── Validación ───────────────────────────────────────────

    public boolean validarToken(String token) {
        try {
            parsearClaims(token);
            return true;
        } catch (ExpiredJwtException e)  { log.warn("Token expirado: {}", e.getMessage()); }
        catch (UnsupportedJwtException e){ log.warn("Token no soportado: {}", e.getMessage()); }
        catch (MalformedJwtException e)  { log.warn("Token malformado: {}", e.getMessage()); }
        catch (SecurityException e)      { log.warn("Firma inválida: {}", e.getMessage()); }
        catch (IllegalArgumentException e){ log.warn("Token vacío: {}", e.getMessage()); }
        return false;
    }

    // ── Utilitarios privados ─────────────────────────────────

    private Claims parsearClaims(String token) {
        return Jwts.parser()
                .verifyWith(clave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey clave() {
        byte[] keyBytes = Decoders.BASE64.decode(
                java.util.Base64.getEncoder().encodeToString(jwtSecret.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

