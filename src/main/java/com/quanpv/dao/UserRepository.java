package com.quanpv.dao;

import com.quanpv.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends CrudRepository<User, Integer>{
    User findUserByUsernameAndPassword(String username, String password);
}
