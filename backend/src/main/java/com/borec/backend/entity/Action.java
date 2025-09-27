package com.borec.backend.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 * create table action(
id int primary key,
type varchar(50),
time bigint,
date1 date
)

 */

@Entity
public class Action {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    Long id;

    @Column(name = "type", nullable = false)
    String type;

    @Column(name = "time", unique = true, nullable = false)
    Long time;

    @Column(name = "date", nullable = false)
    Date date;

	public Action(ActionType type, Long time) {
		super();
		this.type = type.toString();
		this.time = time;
		this.date = new Date(time);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
