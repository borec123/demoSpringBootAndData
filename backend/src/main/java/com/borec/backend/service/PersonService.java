package com.borec.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.borec.backend.entity.Person;
import com.borec.backend.repository.PersonRepository;

@Service
@Transactional
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional(readOnly = true)
    public List<Person> list() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        List<Person> list =  personRepository.findAll();


/*        .forEach(e -> {
            list.add(new Person().setPrice(e.getPrice())
                    .setId(e.getId())
                    .setTitle(e.getTitle())
                    .setDescription(e.getDescription())
                    .setFountain(e.getFountain()));
        });
        */

        return list;
    }

    @Transactional
    public Person insert(Person person) {
        return personRepository.save(person);
    }

    public Long findByFirstNameCrossJoin(String name) {
        return personRepository.findByFirstNameCrossJoin(name);
    }


}
