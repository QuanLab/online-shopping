package com.quanpv.dao;

import com.quanpv.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Transactional
public interface UserRepository extends CrudRepository<User, Integer>{
    User findUserByUsernameAndPassword(String username, String password);
}
