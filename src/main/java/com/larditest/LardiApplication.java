package com.larditest;

import com.larditest.DAO.ContactsRepository;
import com.larditest.DAO.UserRepository;
import com.larditest.Entities.Contact;
import com.larditest.Entities.User;
import com.larditest.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class LardiApplication implements CommandLineRunner {
    @Autowired
    UserRepository repository;
    @Autowired
    ContactsRepository repository1;
    public static void main(String[] args) {
        SpringApplication.run(LardiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(Arrays.toString(repository1.findByMobilePhoneContainingAndOwnerIs("25",repository.findByUsername("Bob")).toArray()));
    }
}
