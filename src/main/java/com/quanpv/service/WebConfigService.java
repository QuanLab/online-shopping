package com.quanpv.service;


import com.quanpv.dao.WebConfigRepository;
import com.quanpv.model.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class WebConfigService {

    @Autowired
    private WebConfigRepository repository;

    public Map<String, String> getAll(){
        Map<String,String> mapConfiguration = new HashMap<>();
        Iterator<WebConfig> iter = repository.findAll().iterator();
        while (iter.hasNext()) {
            WebConfig item = iter.next();
            mapConfiguration.put(item.getName(), item.getValue());
        }
        return mapConfiguration;
    }

    public WebConfig getById(int id){
        return repository.findById(id).orElse(null);
    }

    public void save(WebConfig webConfig){
        repository.save(webConfig);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

}
