package com.masterpeace.atmosphere.dao;

import com.masterpeace.atmosphere.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Implemented by classes that access the User data store
 */
public interface UserRepository extends CrudRepository<User, Long>{
    User findByEmail(String email);
}
