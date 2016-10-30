package com.larditest.dao;

import com.larditest.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by PC on 28.10.2016.
 */
@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    public User findByUsername(String username);


}
