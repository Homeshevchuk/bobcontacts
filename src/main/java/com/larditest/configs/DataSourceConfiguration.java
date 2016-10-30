package com.larditest.configs;

import com.larditest.dao.UserDao;
import com.larditest.dao.UserDaoDBImpl;
import com.larditest.dao.UserDaoJsonImpl;
import com.larditest.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by PC on 28.10.2016.
 */
@Configuration
public class DataSourceConfiguration {
    @Autowired
    ApplicationContext context;

    @Bean
    @Conditional(MySqlCondition.class)
    public UserDao getSqlDao() {
        return new UserDaoDBImpl();
    }

    @Bean
    @Conditional(JsonCondition.class)
    public UserDao getJsonDao() {
        return new UserDaoJsonImpl(context);
    }
}
