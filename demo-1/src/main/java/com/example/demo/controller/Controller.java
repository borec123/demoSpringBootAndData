package com.example.demo.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.WatchEntity;
import com.example.demo.pojo.Watch;
import com.example.demo.service.WatchService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class Controller {

	@Autowired
	private WatchService watchService;
	

	@GetMapping("/list")
	public List<WatchEntity> list() {
        try {
			List<WatchEntity> all = watchService.list();
			return all;
        } 
        catch (Exception e) {
        	handleError(e);
        }
		return null;
        
	}
	
	@PutMapping("/insertwatch")
	//@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<Watch> insertWatch(@RequestBody Watch newWatch) {
		try {
    		  watchService.insert(newWatch);
	    	  return ResponseEntity.created(null).body(newWatch);
		}
        catch (Exception e) {
        	handleError(e);
        }
		return null;
	}
	
	@PutMapping("/updatewatch/{id}")
	//@ResponseStatus(HttpStatus..CREATED)
	ResponseEntity<Watch> replaceWatch(@RequestBody Watch newWatch, @PathVariable Long id) {


	  watchService.update(newWatch, id);
	  return ResponseEntity.accepted().body(newWatch);

	  }	
	  
	  @ResponseBody
	  @ExceptionHandler(Exception.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  String handleError(Exception ex) {
		  ex.printStackTrace();
	    return ex.getMessage();
	  }
	
	@PostConstruct
	public void init() {
		
		watchService.createMockData();
		
		 		 
	}

	@PostMapping(path = "/post", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public String post(@RequestBody String text) {
		return text;
	}

}
