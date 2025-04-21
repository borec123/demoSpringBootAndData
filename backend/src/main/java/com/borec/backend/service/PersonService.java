package com.borec.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.borec.backend.entity.Person;
import com.borec.backend.repository.PersonRepository;

@Service
@Transactional
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public List<Person> list() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        List<Person> list =  personRepository.findAll();

        return list;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Person insert(Person person) {
        return personRepository.save(person);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Double transfer() {
    	
    	List<Person> list = list();
    	
    	Person from = list.get(0);
    	Person to = list.get(1);
    	
    	Double amount = Double.valueOf(10.0d);
    	
    	from.setScore(from.getScore() - amount);
        personRepository.save(from);
        
        try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
    	to.setScore(to.getScore() + amount);
        personRepository.save(to);
        return amount;
    }

    public Long findByFirstNameCrossJoin(String name) {
        return personRepository.findByFirstNameCrossJoin(name);
    }


}
