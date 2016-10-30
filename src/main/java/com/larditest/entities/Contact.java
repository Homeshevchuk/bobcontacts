package com.larditest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by PC on 25.10.2016.
 */
@Entity
public class Contact {
    @Id
    @GeneratedValue
    private long id;
    @Size(min = 4,message = "Surname length must be more than 4.")
    private String surname;
    @Size(min = 4,message = "Name length must be more than 4.")
    private String name;
    @Size(min = 4,message = "Last name length must be more than 4.")
    private String middleName;
    @NotNull
    @Pattern(regexp = "^\\+380\\([0-9]{2,2}\\)[0-9]{7,7}$",message = "Phone format : '+380(66)1234567'")
    private String mobilePhone;
    @Pattern(regexp = "^$|^\\+380\\([0-9]{2,3}\\)[0-9]{6,7}$",message = "Phone format : '+380(66)1234567'")
    private String homePhone;
    private String address;
    @Pattern(regexp = "^$|^.*@.*\\..*$",message = "Email format: 'user@example.com'")
    private String email;
    @ManyToOne
    @JoinColumn(name = "owner")
    @JsonIgnore
    private User owner;
    public Contact() {
    }

    public Contact(String surname, String name, String lastName, String mobilePhone, String homePhone, String address, String email) {
        this.surname = surname;
        this.name = name;
        this.middleName = lastName;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.address = address;
        this.email = email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", surname='" + surname + '\'' +

                ", name='" + name + '\'' +
                ", middleName='" + middleName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", homePhone='" + homePhone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
