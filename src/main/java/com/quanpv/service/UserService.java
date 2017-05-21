package com.quanpv.service;

import com.quanpv.dao.UserRepository;
import com.quanpv.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User get(String id){
        return userRepository.findOne(id);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public void delete(User product) {
        userRepository.delete(product);
    }

    public Iterable<User> getAllUser(){
        return userRepository.findAll();
    }

}
