package com.larditest.dao;

import com.larditest.entities.User;


import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by PC on 28.10.2016.
 */

public interface UserDao{

    User save (User entity);
    User findByUsername(String username);
    void deleteAll();
}
