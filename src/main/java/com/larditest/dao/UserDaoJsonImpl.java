package com.larditest.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.larditest.entities.Contact;
import com.larditest.entities.User;

import org.springframework.context.ApplicationContext;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by PC on 28.10.2016.
 */

public class UserDaoJsonImpl implements UserDao {
    private File users;


    public UserDaoJsonImpl(ApplicationContext context) {
        String path = context.getEnvironment().getProperty("storePath");
        File directory = new File(path + "\\LardiDataStore");
        path += "\\LardiDataStore";
        if (!directory.exists()) {
            directory.mkdir();
        }
        File users = new File(path + "\\users.json");

        if (!users.exists()) {
            try {
                users.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.users = users;
    }

    @Override
    public User save(User entity) {
        ObjectMapper mapper = new ObjectMapper();
        List<User> userList;
        if (isEmpty(this.users)) {
            userList = new ArrayList<>();
            userList.add(entity);
            try {
                mapper.writeValue(this.users, userList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {

                userList = new ArrayList<>(Arrays.asList(mapper.readValue(this.users, User[].class)));
                if (userList.contains(entity)){
                    User userBefore = userList.get(userList.indexOf(entity));
                    List<Contact> contactList = entity.getContacts();
                    if(userBefore.getContacts()!=null) {
                        if (contactList.size() > userBefore.getContacts().size() && userBefore.getContacts().size()!=0) {
                            contactList.get(contactList.size() - 1).setId(contactList.get(contactList.size() - 2).getId() + 1);
                        }
                    }
                    userBefore.setContacts(contactList);
                    int index = userList.indexOf(userBefore);
                    userList.remove(index);
                    userList.add(index,userBefore);
                    mapper.writeValue(this.users,userList);
                }else {
                    entity.setId(userList.get(userList.size() - 1).getId() + 1);
                    entity.setContacts(new ArrayList<>());
                    userList.add(entity);
                    mapper.writeValue(this.users, userList);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return entity;
    }

    @Override
    public User findByUsername(String username) {
        ObjectMapper mapper = new ObjectMapper();
        if (isEmpty(this.users)) return null;
        try {
            List<User> userList = Arrays.asList(mapper.readValue(this.users, User[].class));
            for (User user : userList) {
                if (user.getUsername().equals(username)) {
                    if(user.getContacts()==null){
                        user.setContacts(new ArrayList<>());
                    }
                    return user;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteAll() {
        String path = this.users.getPath();
        this.users.delete();
        this.users = new File(path);
    }

    private boolean isEmpty(File file) {
        BufferedReader br = null;
        boolean result = false;
        try {
            br = new BufferedReader(new FileReader(file));
            if (br.readLine() == null) {
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }
}
