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
			handleError(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;

	}

	@PutMapping("/insertwatch")
	// @ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<Watch> insertWatch(@RequestBody Watch newWatch) {
		try {
			watchService.insert(newWatch);
			return ResponseEntity.created(null).body(newWatch);
		} catch (org.springframework.http.converter.HttpMessageNotReadableException e) {
			handleError(e, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			handleError(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}

	@PutMapping("/updatewatch/{id}")
	// @ResponseStatus(HttpStatus..CREATED)
	ResponseEntity<Watch> replaceWatch(@RequestBody Watch newWatch, @PathVariable Long id) {
		try {
			watchService.update(newWatch, id);
			return ResponseEntity.ok().body(newWatch);
		} catch (EntityNotFoundException e) {
			handleError(e, HttpStatus.NOT_FOUND);
		} catch (org.springframework.http.converter.HttpMessageNotReadableException e) {
			handleError(e, HttpStatus.BAD_REQUEST);
		}  catch (Exception e) {
			handleError(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	ResponseEntity<Exception> handleError(Exception ex, HttpStatus status) {
		ex.printStackTrace();
		return ResponseEntity.status(status).body(ex);
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
