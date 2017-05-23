package com.quanpv.controller;

import com.quanpv.domain.Item;
import com.quanpv.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/items")
public class ItemController {

    final static Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService service;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        service.save(item);
        logger.debug("Added:: " + item);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }


    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Item item) {
        Item existingItem = service.getById(item.getId());
        if (existingItem == null) {
            logger.debug("Employee with id " + item.getId() + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            service.save(item);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Item> getProduct(@PathVariable("id") int id) {
        Item item = service.getById(id);
        if (item == null) {
            logger.debug("Product with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.debug("Found Product:: " + item);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Item>> getAll() {
        Iterable<Item> products = service.getAll();
        if (!products.iterator().hasNext()) {
            logger.debug("Item does not exists");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        Item item = service.getById(id);
        if (item == null) {
            logger.debug("item with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            service.delete(id);
            logger.debug("Item with id " + id + " deleted");
            return new ResponseEntity<>(HttpStatus.GONE);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "card/{id}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Item>> getByCardId(@PathVariable("id") int id) {
        Iterable<Item> items = service.getByCardId(id);
        if (!items.iterator().hasNext()) {
            logger.debug("Item does not exists");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
