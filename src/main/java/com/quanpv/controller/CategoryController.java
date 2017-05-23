package com.quanpv.controller;

import com.quanpv.domain.Category;
import com.quanpv.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    final static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService service;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        service.save(category);
        logger.debug("Added:: " + category);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateCategory(@RequestBody Category category) {
        Category existingCategory = service.getById(category.getId());
        if (existingCategory == null) {
            logger.debug("Category with id " + category.getId() + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            service.save(category);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Category> getCategory(@PathVariable("id") int id) {
        Category category = service.getById(id);
        if (category == null) {
            logger.debug("Category with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.debug("Found Category:: " + category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Category>> getAllCategorys() {
        Iterable<Category> categories = service.getAll();
        if (!categories.iterator().hasNext()) {
            logger.debug("Category does not exists");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") int id) {
        Category category = service.getById(id);
        if (category == null) {
            logger.debug("Category with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            service.delete(id);
            logger.debug("Category with id " + id + " deleted");
            return new ResponseEntity<>(HttpStatus.GONE);
        }
    }
}
