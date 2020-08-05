package com.example.demo.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;

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
import org.springframework.web.server.ResponseStatusException;

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
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}

	@PutMapping("/insertwatch")
	// @ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<Watch> insertWatch(@RequestBody Watch newWatch) {
		try {
			watchService.insert(newWatch);
			return ResponseEntity.created(null).body(newWatch);
		} catch (org.springframework.http.converter.HttpMessageNotReadableException e) {
			return ResponseEntity.badRequest().body(newWatch);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(newWatch);
		}
	}

	@PutMapping("/updatewatch/{id}")
	// @ResponseStatus(HttpStatus..CREATED)
	ResponseEntity<Watch> replaceWatch(@RequestBody Watch newWatch, @PathVariable Long id) {
		try {
			watchService.update(newWatch, id);
			return ResponseEntity.ok().body(newWatch);
		} /*
			 * catch (EntityNotFoundException e) { throw new
			 * ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); }
			 */ catch (org.springframework.http.converter.HttpMessageNotReadableException e) {
			return ResponseEntity.badRequest().body(newWatch);
		}  catch (Exception e) {
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

}
