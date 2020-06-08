package com.example.demo.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Car;
import com.example.demo.service.CarRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class Controller {

	@Autowired
	private CarRepository carRepository;

	@GetMapping("/reverse/{text}")
	public String reverse(@PathVariable String text) {
        String json;
        try {
			List<Car> a = carRepository.findAll();
			a.forEach(s -> System.out.println(s.getName()));
	
	        ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(a);
        } catch (Exception e) {
            return e.toString();
        }
        
		return json;
	}
	
	@PostConstruct
	public void init() {
		
		  carRepository.save(new Car().setName("Auto 1") ); 
		  carRepository.save(new Car().setName("Auto 2") ); 
		  carRepository.flush();
		 		 
	}

	@GetMapping("/reverse2/{text}")
	public String reverse2(@PathVariable String text) {
		return "kokot2";
	}

	@PostMapping(path = "/post", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public String post(@RequestBody String text) {
		return text;
	}

}
