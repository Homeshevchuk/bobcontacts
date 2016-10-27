package com.larditest.DAO;

import com.larditest.Entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by PC on 28.10.2016.
 */
public interface UserRepository<User> extends CrudRepository<User,Long> {
    public User findByUsername(String username);


}
