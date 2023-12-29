package com.backend.reactive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.reactive.entity.Person;
import com.backend.reactive.repository.PersonRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public Flux<Person> findAll() {
        return personRepository.findAll();
    }

    public Mono<Long> findByFirstNameCrossJoin(String name) {
        return personRepository.findByFirstNameCrossJoin(name);
    }

    @Transactional
    public Mono<Person> insert(Person person) {
        return personRepository.save(person);
    }

}
