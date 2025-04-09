package org.security.jwt.model;

public class JWtResponse {
    private String token;

    public JWtResponse(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
