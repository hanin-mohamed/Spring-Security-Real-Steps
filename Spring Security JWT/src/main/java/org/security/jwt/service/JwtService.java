package org.security.jwt.service;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.security.jwt.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    private final String secret_key = "MY_SECRET_KEY"; // Secret key for signing the JWT
    private long accessTokenValidity = 60 * 60 * 100; // 1 hour

    private final JwtParser jwtParser;

    public JwtService() {
        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
    }

    public String generateToken(User user) {
        Claims claims = Jwts.claims();
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());

        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(tokenCreateTime)
                .setExpiration(tokenValidity)
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

    private List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }
}