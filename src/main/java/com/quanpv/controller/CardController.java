package com.quanpv.controller;

import com.quanpv.domain.Card;
import com.quanpv.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cards")
public class CardController {

    final static Logger logger = LoggerFactory.getLogger(CardController.class);

    @Autowired
    private CardService service;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Card> add(@RequestBody Card card) {
        service.save(card);
        logger.debug("Added:: " + card);
        return new ResponseEntity<>(card, HttpStatus.CREATED);
    }


    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateProduct(@RequestBody Card card) {
        Card existingCard = service.getById(card.getId());
        if (existingCard == null) {
            logger.debug("Card with id " + card.getId() + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            service.save(card);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Card> getCard(@PathVariable("id") int id) {
        Card card = service.getById(id);
        if (card == null) {
            logger.debug("Card with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.debug("Found:: " + card);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Card>> getAllProducts() {
        Iterable<Card> cards = service.getAll();
        if (!cards.iterator().hasNext()) {
            logger.debug("Card does not exists");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") int id) {
        Card product = service.getById(id);
        if (product == null) {
            logger.debug("Card with id " + id + " does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            service.delete(id);
            logger.debug("Card with id " + id + " deleted");
            return new ResponseEntity<>(HttpStatus.GONE);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "customer/{id}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Card>> getCardByCustomerId(@PathVariable("id") int id) {
        Iterable<Card> cards = service.getByCustomerId(id);
        if (!cards.iterator().hasNext()) {
            logger.debug("Card does not exists");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }
}
