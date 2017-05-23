package com.quanpv.service;

import com.quanpv.dao.UserRepository;
import com.quanpv.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public Iterable<User> getAll(){
        return repository.findAll();
    }

    public User getById(int id){
        return repository.findOne(id);
    }

    public User getUserByUsernameAndPassword(String userName, String password){
        return repository.findUserByUsernameAndPassword(userName, password);
    }

    public void save(User product){
        repository.save(product);
    }

    public void delete(int id) {
        repository.delete(id);
    }

}
