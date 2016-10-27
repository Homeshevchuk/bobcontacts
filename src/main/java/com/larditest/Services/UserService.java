package com.larditest.Services;

import com.larditest.DAO.UserRepository;
import com.larditest.Entities.User;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * Created by PC on 25.10.2016.
 */
@Service
public class UserService {
    @Autowired
    UserRepository repository;
    public static void register(User user) {
    }
}
