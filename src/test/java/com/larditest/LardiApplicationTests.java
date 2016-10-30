package com.larditest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.larditest.configs.DataSourceConfiguration;
import com.larditest.configs.JsonCondition;
import com.larditest.configs.MySqlCondition;

import com.larditest.configs.WebSecurityConfig;
import com.larditest.dao.UserDao;
import com.larditest.endpoints.MainController;
import com.larditest.entities.Contact;
import com.larditest.entities.User;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;


import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import javax.servlet.Filter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@WebMvcTest()
@ContextConfiguration(classes = {DataSourceConfiguration.class, JsonCondition.class, MySqlCondition.class, MainController.class})
@Import(WebSecurityConfig.class)
public class LardiApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    UserDao dao;

    @Autowired
    private ObjectMapper mapper;
    private User testUser;

    @BeforeClass
    public static void setupClass() throws IOException {
        String path = System.getProperty("lardi.conf");
        Properties loadProps = new Properties();
        loadProps.load(new InputStreamReader(new FileInputStream(path)));
        Properties datasourceProps = new Properties();

        if (loadProps.containsKey("host")) {
            datasourceProps.setProperty("spring.datasource.driver-class-name", "com.mysql.jdbc.Driver");
            datasourceProps.setProperty("spring.datasource.url", "jdbc:mysql://" + loadProps.getProperty("host"));
            datasourceProps.setProperty("spring.datasource.username", loadProps.getProperty("username"));
            datasourceProps.setProperty("spring.datasource.password", loadProps.getProperty("password"));
        } else {
            datasourceProps.setProperty("storePath", loadProps.getProperty("path"));
            datasourceProps.setProperty("spring.autoconfigure.exclude", "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration");

        }

        System.getProperties().putAll(datasourceProps);
    }

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setup() throws Exception {

        testUser = new User("testuser", "testpassword", "fullname");
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        this.mockMvc.perform(post("/signup").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(testUser))).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void createContact() throws Exception {

        Contact contact = new Contact("contactTestSurname", "contactStringName", "contactLastname", "+380(98)6780723", null, null, null);
        String json = mapper.writeValueAsString(contact);
        //CREATE
        this.mockMvc.perform(get("/Contacts/getContacts").with(user(testUser.getUsername()).password(testUser.getPassword()))).andExpect(content().string("[]"));
        this.mockMvc.perform(post("/Contacts/addContact").contentType(MediaType.APPLICATION_JSON).content(json).with(user(testUser.getUsername()).password(testUser.getPassword()))).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/Contacts/getContacts").with(user(testUser.getUsername()).password(testUser.getPassword()))).andExpect(jsonPath("$", Matchers.hasSize(1)));
        dao.save(testUser);//cleanContacts

    }

    @Test
    public void updateContact() throws Exception {

        Contact contact = new Contact("contactTestSurname", "contactStringName", "contactLastname", "+380(98)6780723", null, null, null);
        String json = mapper.writeValueAsString(contact);
        this.mockMvc.perform(post("/Contacts/addContact").contentType(MediaType.APPLICATION_JSON).content(json).with(user(testUser.getUsername()).password(testUser.getPassword()))).andExpect(status().is2xxSuccessful());
        contact.setName("edited");
        json = mapper.writeValueAsString(contact);
        this.mockMvc.perform(post("/Contacts/updateContact").contentType(MediaType.APPLICATION_JSON).content(json).with(user(testUser.getUsername()).password(testUser.getPassword()))).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/Contacts/getContacts").with(user(testUser.getUsername()).password(testUser.getPassword()))).andExpect(jsonPath("$[0].name", Matchers.equalTo("edited")));
        dao.save(testUser);//cleanContacts
    }

    @Test
    public void deleteContact() throws Exception {
        Contact contact = new Contact("contactTestSurname", "contactStringName", "contactLastname", "+380(98)6780723", null, null, null);
        String json = mapper.writeValueAsString(contact);
        this.mockMvc.perform(get("/Contacts/getContacts").with(user(testUser.getUsername()).password(testUser.getPassword()))).andExpect(content().string("[]"));
        this.mockMvc.perform(post("/Contacts/addContact").contentType(MediaType.APPLICATION_JSON).content(json).with(user(testUser.getUsername()).password(testUser.getPassword()))).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/Contacts/getContacts").with(user(testUser.getUsername()).password(testUser.getPassword()))).andExpect(jsonPath("$", Matchers.hasSize(1)));
        this.mockMvc.perform(post("/Contacts/deleteContact").contentType(MediaType.APPLICATION_JSON).content(json).with(user(testUser.getUsername()).password(testUser.getPassword()))).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/Contacts/getContacts").with(user(testUser.getUsername()).password(testUser.getPassword()))).andExpect(content().string("[]"));

    }
    
}