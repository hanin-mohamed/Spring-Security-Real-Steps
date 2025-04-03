package org.security.jwt.auth;


import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.security.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;

@Component
@Order(1)
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtService.resolveToken(request);
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            System.out.println("Token: " + token);

            Claims claims = jwtService.resolveClaims(request);

            if (claims !=null  & !jwtService.isTokenExpired(claims.getExpiration())){

                String email = claims.getSubject();
                System.out.println("email : "+email);
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, "",new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);

        }
    }catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);

    }
}
