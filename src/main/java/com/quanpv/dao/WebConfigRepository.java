package com.quanpv.dao;


import com.quanpv.model.WebConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface WebConfigRepository extends CrudRepository<WebConfig, Integer> {
    Iterable<WebConfig> findAll();
}
