package com.quanpv.controller;

import com.quanpv.domain.Customer;
import com.quanpv.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    final static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService service;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Customer> addEmployee(@RequestBody Customer customer) {
        service.save(customer);
        logger.debug("Added:: " + customer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateEmployee(@RequestBody Customer customer) {
        Customer existingCustomer = service.getById(customer.getId());
        if (existingCustomer == null) {
            logger.debug("Customer with id " + customer.getId() + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            service.save(customer);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Customer> getEmployee(@PathVariable("id") int id) {
        Customer customer = service.getById(id);
        if (customer == null) {
            logger.debug("Customer with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.debug("Found Customer:: " + customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Customer>> getAllEmployees() {
        Iterable<Customer> customers = service.getAll();
        if (!customers.iterator().hasNext()) {
            logger.debug("Customer does not exists");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") int id) {
        Customer customer = service.getById(id);
        if (customer == null) {
            logger.debug("Customer with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            service.delete(id);
            logger.debug("Customer with id " + id + " deleted");
            return new ResponseEntity<>(HttpStatus.GONE);
        }
    }
}
