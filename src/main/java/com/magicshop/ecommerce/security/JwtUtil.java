package com.magicshop.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    // ✅ Genera un token con correo y rol
    public String generateToken(String username, String userRole) {
        SecretKey key = getSigningKey();

        return Jwts.builder()
                .setSubject(username)
                .claim("role", userRole) // "role": "ADMIN", etc.
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Extrae el correo (username)
    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ✅ Extrae el rol
    public String getRoleFromToken(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    // ✅ Valida si el token es válido y no ha expirado
    public boolean validateToken(String token, String correo) {
        final String usernameFromToken = getUsernameFromToken(token);
        return (usernameFromToken != null &&
                usernameFromToken.equals(correo) &&
                !isTokenExpired(token));
    }

    // =====================
    // 🔒 Métodos auxiliares
    // =====================

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
