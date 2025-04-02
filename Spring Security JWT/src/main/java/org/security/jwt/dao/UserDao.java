package org.security.jwt.dao;

import org.security.jwt.entity.User;

public interface UserDao {
    User findUserByEmail(String email);
}
