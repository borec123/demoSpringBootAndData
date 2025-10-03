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
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.borec.backend.entity.Action;
import com.borec.backend.entity.Person;
import com.borec.backend.entity.Zprava;
import com.borec.backend.pojo.PersonResponse;
import com.borec.backend.pojo.ZpravyResponse;
import com.borec.backend.service.ActionService;
import com.borec.backend.service.PersonService;
import com.borec.backend.service.ZpravaService;



@RestController
public class Controller {

	@Autowired
	private PersonService personService;

	@Autowired
	private ActionService actionService;

	@Autowired
	private ZpravaService zpravaService;


	@PutMapping("/saveZprava")
	ResponseEntity<Zprava> saveZprava(@RequestBody Zprava zprava) {
		try {
			Zprava p = zpravaService.insert(zprava);
			return ResponseEntity.created(null).body(p);
		} catch (org.springframework.http.converter.HttpMessageNotReadableException e) {
			return ResponseEntity.badRequest().body(zprava);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(zprava);
		}
	}

	@GetMapping("/listzprava")
	public ResponseEntity<ZpravyResponse> listzprava() {
		try {
			List<Zprava> all = zpravaService.list();
			return ResponseEntity.ok(new ZpravyResponse(all));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GetMapping("/listForClientApplication")
	public ResponseEntity<ZpravyResponse> listForClientApplication() {
		try {
			List<Zprava> all = zpravaService.list();
			return ResponseEntity.ok(new ZpravyResponse(all));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@PutMapping("/insertzprava")
	ResponseEntity<Zprava> insertZprava(@RequestBody Zprava zprava) {
		try {
			Zprava p = zpravaService.insert(zprava);
			return ResponseEntity.created(null).body(p);
		} catch (org.springframework.http.converter.HttpMessageNotReadableException e) {
			return ResponseEntity.badRequest().body(zprava);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(zprava);
		}
	}


	
	@GetMapping("/listtime")
	public ResponseEntity<List<Action>> listTime() {
		try {
			/*
			 * Action a = new Action(ActionType.START, System.currentTimeMillis()); return
			 * List.of(a);
			 */
			List<Action> all = actionService.list();
			return ResponseEntity.ok(all);
			
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}
	@GetMapping("/list")
	public ResponseEntity<PersonResponse> list() {
		try {
			// Thread.sleep(1000);
			/*
			 * List<Person> all = List.of(new Person("Roman", "Sikora"), new Person("John",
			 * "Smith")); return ResponseEntity.ok(new PersonResponse(all));
			 */
			List<Person> all = personService.list();
			return ResponseEntity.ok(new PersonResponse(all));
//            return new ResponseEntity<>(new PersonResponse(all), HttpStatus.NOT_MODIFIED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}

	@PutMapping("/insertwatch")
	ResponseEntity<Person> insertWatch(@RequestBody Person person) {
		try {
			Person p = personService.insert(person);
			return ResponseEntity.created(null).body(p);
		} catch (org.springframework.http.converter.HttpMessageNotReadableException e) {
			return ResponseEntity.badRequest().body(person);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(person);
		}
	}

	@GetMapping("/transfer")
	ResponseEntity<Double> transfer() {
		Double d = null;
		try {
			d = personService.transfer();
			return ResponseEntity.ok(d);
		} catch (org.springframework.http.converter.HttpMessageNotReadableException e) {
			return ResponseEntity.badRequest().body(d);
		} catch (CannotAcquireLockException e) {
			System.out.println("SQL Transaction Deadlock: " + e);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(d);
		}
		catch (Exception e) {
			System.out.println("Error: " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(d);
		}
	}

	@GetMapping(value = "/crossjoin", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public Long findByFirstNameCrossJoin(@RequestParam("name") String name) {
		return personService.findByFirstNameCrossJoin(name);
	}

	/*
	 * 
	 * @PutMapping("/updatewatch/{id}") ResponseEntity<Watch>
	 * replaceWatch(@Valid @RequestBody Watch newWatch, @PathVariable Long id) { try
	 * { watchService.update(newWatch, id); return
	 * ResponseEntity.ok().body(newWatch); } catch (NoSuchElementException e) {
	 * return ResponseEntity.status(HttpStatus.NOT_FOUND).body(newWatch); } catch
	 * (EntityNotFoundException e) { return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).body(newWatch); //throw new
	 * ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()); } catch
	 * (org.springframework.http.converter.HttpMessageNotReadableException e) {
	 * return ResponseEntity.badRequest().body(newWatch); } catch (Exception e) {
	 * return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(newWatch); } }
	 * 
	 * @PostConstruct public void init() {
	 * 
	 * watchService.createMockData();
	 * 
	 * }
	 * 
	 * @PostMapping(path = "/post", consumes = MediaType.TEXT_PLAIN_VALUE, produces
	 * = MediaType.APPLICATION_XML_VALUE) public String post(@RequestBody String
	 * text) { return text; }
	 */
}
