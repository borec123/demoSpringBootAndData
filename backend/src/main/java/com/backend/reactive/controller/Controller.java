package com.backend.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.reactive.entity.Person;
import com.backend.reactive.service.PersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class Controller {

    @Autowired
    PersonService personService;

    @GetMapping(value="/list", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Flux<Person> getAllBooks() {
/*
        List<Person> list = List.of(new Person("Pierun"));
         return Mono.just(list);
*/
        return personService.findAll();
    }

    @GetMapping(value="/crossjoin", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Mono<Long> findByFirstNameCrossJoin(@RequestParam("name") String name) {
        return personService.findByFirstNameCrossJoin(name);
    }

    @PutMapping("/insertwatch")
    ResponseEntity<Mono<Person>> insertWatch( @RequestBody Person person) {
        Mono<Person> p = null;
        try {
            p = personService.insert(person);
            return ResponseEntity.created(null).body(p);
        } catch (org.springframework.http.converter.HttpMessageNotReadableException e) {
            return ResponseEntity.badRequest().body(p);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(p);
        }
    }

}
