/*
 * package com.example.demo.controller;
 * 
 * import javax.persistence.EntityNotFoundException;
 * 
 * import org.springframework.http.HttpStatus; import
 * org.springframework.web.bind.annotation.ControllerAdvice; import
 * org.springframework.web.bind.annotation.ExceptionHandler; import
 * org.springframework.web.bind.annotation.ResponseBody; import
 * org.springframework.web.bind.annotation.ResponseStatus;
 * 
 * @ControllerAdvice public class WatchNotFoundAdvice {
 * 
 * @ResponseBody
 * 
 * @ExceptionHandler(EntityNotFoundException.class)
 * 
 * @ResponseStatus(HttpStatus.NOT_FOUND) String
 * employeeNotFoundHandler(EntityNotFoundException ex) { return ex.getMessage();
 * } }
 */