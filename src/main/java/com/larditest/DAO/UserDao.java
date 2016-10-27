package com.larditest.DAO;

import com.larditest.Entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by PC on 28.10.2016.
 */

public interface UserDao<User>{

    User save (User entity);
    User findByUsername(String username);
}
