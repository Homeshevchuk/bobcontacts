package com.larditest.Entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by PC on 25.10.2016.
 */
@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;
    @Size(min = 3,message = "Username length must be more than 3.")
    @Pattern(regexp = "^[a-zA-Z ]+$",message = "Username can contains english letters only.")
    private String username;
    @Size(min = 5, message = "Password length must be more than 5.")
    private String password;
    @Size(min = 5,message = "Full name length must be more than 5.")
    private String fullName;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "owner",orphanRemoval = true)
    List<Contact> contacts;

    public User() {
    }

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.contacts = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", contacts=" + Arrays.toString(contacts.toArray()) +
                '}';
    }
}
