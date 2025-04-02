package org.security.jwt.dao;

import org.security.jwt.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public User findUserByEmail(String email) {

        // Check for user by email
        User user = new User(email,"12345");
        user.setFirstName("Hanin");
        user.setLastName("Mohamed");
        return user;
    }
}
