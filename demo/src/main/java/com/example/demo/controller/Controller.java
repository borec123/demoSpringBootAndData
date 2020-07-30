package com.example.demo.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Watch;
import com.example.demo.service.WatchRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class Controller {

	@Autowired
	private WatchRepository watchRepository;

	@GetMapping("/list")
	public String list() {
        String json;
        try {
			List<Watch> a = watchRepository.findAll();
			a.forEach(s -> System.out.println(s.getTitle()));
	
	        ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(a);
        } catch (Exception e) {
            return e.toString();
        }
        
		return json;
	}
	
	  @PutMapping("/employees/{id}")
	  Watch replaceEmployee(@RequestBody Watch newWatch, @PathVariable Long id) {

	    return watchRepository.findById(id)
	      .map(watch -> {
	    	  watch.setTitle(newWatch.getTitle());
	    	  watch.setDescription(newWatch.getDescription());
	    	  watch.setPrice(newWatch.getPrice());
	    	  watch.setFountainString(newWatch.getFountainString());
	        return watchRepository.save(watch);
	      })
	      .orElseGet(() -> {
	    	  newWatch.setId(id);
	        return watchRepository.save(newWatch);
	      });
	  }	
	
	@PostConstruct
	public void init() {
		
		  watchRepository.save(new Watch().setTitle("Mock watch 1") ); 
		  watchRepository.save(new Watch().setTitle("Mock watch 2") ); 
		  watchRepository.flush();
		 		 
	}

	@PostMapping(path = "/post", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public String post(@RequestBody String text) {
		return text;
	}

}
