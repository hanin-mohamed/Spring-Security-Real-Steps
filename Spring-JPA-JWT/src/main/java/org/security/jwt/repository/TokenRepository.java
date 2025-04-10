package org.security.jwt.repository;

import org.security.jwt.entity.Token;
import org.security.jwt.entity.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    Token findByToken(String token);

}
