package com.quanpv.dao;

import com.quanpv.domain.Pack;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PackRepository extends CrudRepository<Pack, Integer>{

}
