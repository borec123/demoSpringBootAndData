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
	
	@Autowired
	private WatchModelAssembler assembler;
	
	

	@GetMapping("/list")
	public String list() {
        String json;
        try {
			List<WatchEntity> all = watchService.list();
	        ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(all);
        } catch (Exception e) {
            return e.toString();
        }
        
		return json;
	}
	
	@GetMapping("/employees/{id}")
	EntityModel<Watch> one(@PathVariable Long id) {

		/*
		 * Watch employee = watchService.findById(id) ;
		 * 
		 * return EntityModel.of(employee, //
		 * linkTo(methodOn(Controller.class).one(id)).withSelfRel(),
		 * linkTo(methodOn(Controller.class).all()).withRel("employees"));
		 */	
	
		return null;
	}
	
	@PutMapping("/watch/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<Watch> replaceWatch(@RequestBody Watch newWatch, @PathVariable Long id) {

		//try {
	  watchService.update(newWatch, id);
	  return ResponseEntity.created(null).body(newWatch);
/*		}
		catch(Exception ex) {
			handleError(ex);
		}
*/	  
		/*
		 * EntityModel<Watch> entityModel = assembler.toModel(newWatch);
		 * 
		 * return ResponseEntity //
		 * .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
		 * .body(entityModel);
		 */	
	  }	
	  
	  @ResponseBody
	  @ExceptionHandler(Exception.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  String handleError(Exception ex) {
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
