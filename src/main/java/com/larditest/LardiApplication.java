package com.larditest;

import com.larditest.DAO.ContactsRepository;
import com.larditest.DAO.UserDao;
import com.larditest.DAO.UserDaoJsonImpl;
import com.larditest.DAO.UserRepository;
import com.larditest.Entities.Contact;
import com.larditest.Entities.User;
import com.larditest.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class LardiApplication implements CommandLineRunner {
    @Autowired
    ApplicationContext context;
    UserDao<User> dao;
    @Autowired
    ContactsRepository repository1;
    public static void main(String[] args) {
        SpringApplication.run(LardiApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        dao=context.getBean(dao.getClass());
        System.out.println(dao);
    }
}
