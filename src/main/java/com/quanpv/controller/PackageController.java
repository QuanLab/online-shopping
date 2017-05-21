package com.quanpv.controller;

import com.quanpv.domain.Product;
import com.quanpv.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pack")
public class PackageController {

    final static Logger logger = LoggerFactory.getLogger(PackageController.class);

    @Autowired
    private ProductService service;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> addEmployee(@RequestBody Product product) {
        service.save(product);
        logger.debug("Added:: " + product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateEmployee(@RequestBody Product product) {
        Product existingProduct = service.getById(product.getId());
        if (existingProduct == null) {
            logger.debug("Employee with id " + product.getId() + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            service.save(product);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> getEmployee(@PathVariable("id") int id) {
        Product employee = service.getById(id);
        if (employee == null) {
            logger.debug("Product with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.debug("Found Product:: " + employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Product>> getAllEmployees() {
        Iterable<Product> products = service.getAll();
        if (!products.iterator().hasNext()) {
            logger.debug("Products does not exists");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") int id) {
        Product product = service.getById(id);
        if (product == null) {
            logger.debug("Product with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            service.delete(id);
            logger.debug("Product with id " + id + " deleted");
            return new ResponseEntity<>(HttpStatus.GONE);
        }
    }

    @RequestMapping(value = "category/{id}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Product>> getProductByCategory(@PathVariable("id") int id) {
        Iterable<Product> products = service.getByCategoryId(id);
        if (!products.iterator().hasNext()) {
            logger.debug("Products does not exists");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}