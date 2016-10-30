package com.larditest;

import com.larditest.dao.UserDao;
import com.larditest.entities.Contact;
import com.larditest.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.persistence.ManyToOne;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Configuration
@SpringBootApplication
@ConfigurationProperties(locations = "classpath:lardi.conf")
public class LardiApplication implements CommandLineRunner {
    @Autowired
    UserDao dao;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    public static void main(String[] args) throws IOException {
        LardiApplication lardiApplication = new LardiApplication();



        lardiApplication.setup(args);
    }

    private void setup(String[] args) throws IOException {
        System.out.println("asdasdasd123");
        SpringApplication application = new SpringApplication(LardiApplication.class);
        String path = System.getProperty("lardi.conf");
        Properties loadProps = new Properties();
        loadProps.load(new InputStreamReader(new FileInputStream(path)));
        Properties datasourceProps = new Properties();

        if(loadProps.containsKey("host")){
            datasourceProps.setProperty("spring.datasource.driver-class-name","com.mysql.jdbc.Driver");
            datasourceProps.setProperty("spring.datasource.url","jdbc:mysql://"+loadProps.getProperty("host"));
            datasourceProps.setProperty("spring.datasource.username",loadProps.getProperty("username"));
            datasourceProps.setProperty("spring.datasource.password",loadProps.getProperty("password"));
        }else {
            datasourceProps.setProperty("storePath",loadProps.getProperty("path"));
            datasourceProps.setProperty("spring.autoconfigure.exclude","org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration");

        }

        application.setDefaultProperties(datasourceProps);
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
