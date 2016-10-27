package com.larditest.DAO;

import org.springframework.stereotype.Repository;

/**
 * Created by PC on 28.10.2016.
 */
@Repository
public class UserDaoJsonImpl<User> implements UserDao<User> {


    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }
}
