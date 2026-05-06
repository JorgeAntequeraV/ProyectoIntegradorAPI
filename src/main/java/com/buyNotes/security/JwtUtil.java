package com.buyNotes.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.buyNotes.model.Usuario;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

@Getter
@Component
public class JwtUtil {

    private final Key key;

    // Spring inyecta aquí el valor del properties al construir la clase
    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_" + usuario.getRol().name());  // Usa el rol real del usuario
        claims.put("userId", usuario.getId());                 // ← añade el ID del usuario


        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getNombreUsuario())
                .setIssuedAt(new Date())
                //.setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }





    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }

    public Integer extractUserId(String token) {
        Object val = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId");
        if (val == null) return null;
        if (val instanceof Integer i) return i;
        if (val instanceof Number n) return n.intValue();
        try { return Integer.parseInt(val.toString()); } catch (Exception e) { return null; }
    }

    public String extractRole(String token) {
        Object raw = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role");

        if (raw == null) return null;
        String role = raw.toString();
        // Devuelve tal cual si ya viene con ROLE_
        if (role.startsWith("ROLE_")) return role;
        // Normaliza por si algún token viejo venía como "ADMIN"
        return "ROLE_" + role;
    }
	
}
