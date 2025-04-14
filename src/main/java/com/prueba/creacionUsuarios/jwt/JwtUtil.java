package com.prueba.creacionUsuarios.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // Genera un Key de forma segura a partir de la clave secreta
    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generarToken(String usuario) {
        return Jwts.builder()
                   .setSubject(usuario)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora de expiración
                   .signWith(key, SignatureAlgorithm.HS512)  // Usa HS512 con la clave generada
                   .compact();
    }

}

