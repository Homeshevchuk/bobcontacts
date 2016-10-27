package com.larditest.DAO;

import com.larditest.Entities.Contact;
import com.larditest.Entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by PC on 27.10.2016.
 */
@Repository
public interface ContactsRepository extends CrudRepository<Contact,Long>{
    List<Contact> findBySurnameContainingAndOwnerIs(String containing, User owner);
    List<Contact> findByNameContainingAndOwnerIs(String containing, User owner);
    List<Contact> findByMobilePhoneContainingAndOwnerIs(String containing, User owner);
}
