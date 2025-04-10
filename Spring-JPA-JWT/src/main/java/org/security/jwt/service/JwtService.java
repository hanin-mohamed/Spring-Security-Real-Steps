package org.security.jwt.service;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.security.jwt.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    private final String secret_key = "MY_SECRET_KEY"; // Secret key for signing the JWT
    private final long accessTokenValidity = 60 * 60 * 1000; // 1 hour

    private final JwtParser jwtParser;

    public JwtService() {
        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
    }

    public String generateToken(User user, Map<String, Object> extraClaims) {

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis()+accessTokenValidity))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String resolveToken(HttpServletRequest req) {
        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException e) {
            req.setAttribute("expired", e.getMessage());
            throw e;
        } catch (Exception e) {
            req.setAttribute("invalid", e.getMessage());
            throw e;
        }
    }

    public boolean isTokenExpired(Date expirationDate) {
        return expirationDate.before(new Date());
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
         String username = userDetails.getUsername();
         Claims claims = parseJwtClaims(token);
        return (username.equals(claims.getSubject()) && !isTokenExpired(claims.getExpiration()));
    }
    private List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }
}