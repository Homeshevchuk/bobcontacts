package com.larditest.configs;

import com.larditest.dao.UserDao;
import com.larditest.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDao dao;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/registration","/login.html").permitAll()
                .antMatchers("/Contacts/**","/index.html").authenticated().and()
                .csrf().disable()
                .formLogin().loginPage("/login.html");



    }

    @Autowired
    public void ConfigureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = dao.findByUsername(username);
                UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, AuthorityUtils.createAuthorityList("USER"));
                return userDetails;
            }
        });
    }

}