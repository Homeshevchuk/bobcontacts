package com.larditest.endpoints;

import com.fasterxml.jackson.annotation.JsonView;
import com.larditest.DAO.ContactsRepository;
import com.larditest.DAO.UserDao;
import com.larditest.DAO.UserRepository;
import com.larditest.Entities.Contact;
import com.larditest.Entities.User;
import com.larditest.Services.UserService;
import com.sun.glass.ui.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Created by PC on 25.10.2016.
 */
@RestController
public class MainController {
    @Autowired
    ApplicationContext context;
    UserDao<User> dao;
    {
        dao = context.getBean(dao.getClass());
    }
    @Autowired
    ContactsRepository contactsRepository;
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public void registration(@Validated @RequestBody User user){
        if(dao.findByUsername(user.getUsername())==null){
            dao.save(user);
        }
    }
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public void login(){
    }
    @RequestMapping(value = "/Contacts/addContact",method = RequestMethod.POST)
    public void addContact(@Validated @RequestBody Contact contact, Principal principal){
        User user = dao.findByUsername(principal.getName());
        user.getContacts().add(contact);
        contact.setOwner(user);
        dao.save(user);
    }
    @RequestMapping(value = "/Contacts/updateContact",method = RequestMethod.POST)
    public void updateContact(@Validated @RequestBody Contact updatedContact, Principal principal){
        User user = dao.findByUsername(principal.getName());
        updatedContact.setOwner(user);
        List<Contact> contacts = user.getContacts();
        for(int i = 0;i<contacts.size();i++){
            if(contacts.get(i).getId()==updatedContact.getId()){
                contacts.set(i,updatedContact);
            }
        }
        dao.save(user);
    }
    @RequestMapping(value = "/Contacts/deleteContact",method = RequestMethod.POST)
    public void deleteContact(@Validated @RequestBody Contact toDelete, Principal principal){
        User user = dao.findByUsername(principal.getName());
        List<Contact> contacts = user.getContacts();
        for(int i = 0;i<contacts.size();i++){
            if(contacts.get(i).getId()==toDelete.getId()){
                contacts.remove(i);
            }
        }
        dao.save(user);
    }
  @RequestMapping(value = "/Contacts/getContacts",method = RequestMethod.GET)
    public ResponseEntity<List<Contact>> getContacts(Principal principal){
        User user = dao.findByUsername(principal.getName());
        List<Contact> contacts = user.getContacts();
        return new ResponseEntity<List<Contact>>(contacts, HttpStatus.OK);
    }
    @RequestMapping(value = "/Contacts/filteredBySurname",method = RequestMethod.GET)
    public ResponseEntity<List<Contact>> getContactsFilteredBySurname(@RequestBody String filter, Principal principal){
        User user = dao.findByUsername(principal.getName());
        List<Contact> contacts = contactsRepository.findBySurnameContainingAndOwnerIs(filter,user);
        return new ResponseEntity<List<Contact>>(contacts, HttpStatus.OK);
    }
    @RequestMapping(value = "/Contacts/filteredByName",method = RequestMethod.GET)
    public ResponseEntity<List<Contact>> getContactsFilteredByName(@RequestBody String filter, Principal principal){
        User user = dao.findByUsername(principal.getName());
        List<Contact> contacts = contactsRepository.findByNameContainingAndOwnerIs(filter,user);
        return new ResponseEntity<List<Contact>>(contacts, HttpStatus.OK);
    }
    @RequestMapping(value = "/Contacts/filteredByPhone",method = RequestMethod.GET)
    public ResponseEntity<List<Contact>> getContactsFilteredByPhone(@RequestBody String filter, Principal principal){
        User user = dao.findByUsername(principal.getName());
        List<Contact> contacts = contactsRepository.findByMobilePhoneContainingAndOwnerIs(filter,user);
        return new ResponseEntity<List<Contact>>(contacts, HttpStatus.OK);
    }
}
