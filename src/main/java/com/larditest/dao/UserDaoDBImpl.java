package com.larditest.dao;

import com.larditest.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * Created by PC on 28.10.2016.
 */

public class UserDaoDBImpl implements UserDao {
    @Autowired
    UserRepository repository;

    @Override
    public User save(User entity) {
        repository.save(entity);

        return entity;
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);

    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

}
