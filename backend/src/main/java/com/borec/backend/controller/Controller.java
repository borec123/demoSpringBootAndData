package com.borec.backend.controller;

/**
 * TODO user popis
 *
 * <br>Historie: <br>
 * {{SVN-LOG}}
 * @author user on 2023-12-08
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import com.borec.backend.entity.Person;
import com.borec.backend.pojo.PersonResponse;
import com.borec.backend.service.PersonService;

@RestController
public class Controller {

    @Autowired
    private PersonService personService;

    @GetMapping("/list")
    public ResponseEntity<PersonResponse> list() {
        try {
           // Thread.sleep(1000);
/*            List<Person> all = List.of(new Person("Roman", "Sikora"),
                    new Person("John", "Smith"));
            return ResponseEntity.ok(new PersonResponse(all));*/
            List<Person> all = personService.list();
            return ResponseEntity.ok(new PersonResponse(all));
//            return new ResponseEntity<>(new PersonResponse(all), HttpStatus.NOT_MODIFIED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @PutMapping("/insertwatch")
    ResponseEntity<Person> insertWatch( @RequestBody Person person) {
        try {
            Person p = personService.insert(person);
            return ResponseEntity.created(null).body(person);
        } catch (org.springframework.http.converter.HttpMessageNotReadableException e) {
            return ResponseEntity.badRequest().body(person);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(person);
        }
    }

    /*

    @PutMapping("/updatewatch/{id}")
    ResponseEntity<Watch> replaceWatch(@Valid @RequestBody Watch newWatch, @PathVariable Long id) {
        try {
            watchService.update(newWatch, id);
            return ResponseEntity.ok().body(newWatch);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(newWatch);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(newWatch);
            //throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (org.springframework.http.converter.HttpMessageNotReadableException e) {
            return ResponseEntity.badRequest().body(newWatch);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(newWatch);
        }
    }

    @PostConstruct
    public void init() {

        watchService.createMockData();

    }

    @PostMapping(path = "/post", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public String post(@RequestBody String text) {
        return text;
    }
*/
}
