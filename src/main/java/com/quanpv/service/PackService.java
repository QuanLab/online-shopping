package com.quanpv.service;

import com.quanpv.dao.PackRepository;
import com.quanpv.domain.Pack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackService {

    @Autowired
    private PackRepository repository;


    public Iterable<Pack> getAll(){
        return repository.findAll();
    }

    public Pack getById(int id){
        return repository.findOne(id);
    }

    public void save(Pack pack){
        repository.save(pack);
    }

    public void delete(int id) {
        repository.delete(id);
    }

}
