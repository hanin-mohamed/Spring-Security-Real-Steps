package org.security.jwt.service;

import org.security.jwt.entity.Token;
import org.security.jwt.entity.TokenType;
import org.security.jwt.entity.User;
import org.security.jwt.model.request.LoginRequest;
import org.security.jwt.model.request.RegisterRequest;
import org.security.jwt.model.response.AuthenticationResponse;
import org.security.jwt.repository.TokenRepository;
import org.security.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    // This method is used to register a new user and generate a JWT token
    public AuthenticationResponse register(RegisterRequest request) {

        // use Builder pattern to create a new User object
        User user= User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        User savedUser = userRepository.save(user);
        Map<String, Object> claims = new HashMap<>();
        String jwtToken = jwtService.generateToken(savedUser,claims);
        saveUserToken(savedUser,jwtToken);

        // Save the token in the database
        return new AuthenticationResponse(jwtToken,request.getEmail());
    }

    // This method is used to authenticate the user and generate a JWT token
    public AuthenticationResponse login(LoginRequest request){

        // Authenticate the user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

        // If authentication is successful, generate a JWT token
        User user = userRepository.findUserByEmail(request.getEmail());
        Map<String, Object> claims = new HashMap<>();
        String jwtToken = jwtService.generateToken(user,claims);

        // Save the token in the database
        saveUserToken(user,jwtToken);
        // Return the token and user email in the response
        return new AuthenticationResponse(jwtToken,request.getEmail());
    }

    // This method is used to save the JWT token in the database
    private void saveUserToken(User user, String jwtToken) {

        // Create a new Token object
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        // Save the token in the database
        tokenRepository.save(token);
    }

}
