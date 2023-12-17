package com.borec.backend.pojo;

import java.util.List;

import com.borec.backend.entity.Person;

public class PersonResponse {
	
	final String name = "personresponse";
	int size;
	List<Person> list;
	
	
	public PersonResponse(List<Person> list) {
		super();
		this.list = list;
		size = list.size();
	}


	public String getName() {
		return name;
	}


	public int getSize() {
		return size;
	}


	public List<Person> getList() {
		return list;
	}

	
	
}
