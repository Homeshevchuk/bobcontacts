package com.larditest.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@Component
public class ApplicationStartup
        implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    ApplicationContext context;

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String path = System.getProperty("lardi.conf");
        Properties loadProps = new Properties();
        try {
            loadProps.load(new InputStreamReader(new FileInputStream(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties datasourceProps = new Properties();

        if (loadProps.containsKey("host")) {
            datasourceProps.setProperty("spring.datasource.driver-class-name", "com.mysql.jdbc.Driver");
            datasourceProps.setProperty("spring.datasource.url", "jdbc:mysql://" + loadProps.getProperty("host"));
            datasourceProps.setProperty("spring.datasource.username", loadProps.getProperty("username"));
            datasourceProps.setProperty("spring.datasource.password", loadProps.getProperty("password"));
        } else {
            datasourceProps.setProperty("path", loadProps.getProperty("path"));
        }

    }
} // class