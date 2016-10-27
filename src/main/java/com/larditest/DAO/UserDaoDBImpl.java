package com.larditest.DAO;

import com.larditest.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.JoinColumn;
import java.io.Serializable;

/**
 * Created by PC on 28.10.2016.
 */
@Repository
public class UserDaoDBImpl<User> implements UserDao<User> {
    @Autowired
    UserRepository<User> repository;

    @Override
    public User save(User entity) {
        repository.save(entity);
        return entity;
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

}
