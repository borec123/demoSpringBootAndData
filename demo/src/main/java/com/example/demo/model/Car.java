package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Car extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
    private Long id;

	@Column(name="name", nullable=false, length=255)
    private String name;

	public Long getId() {
		return id;
	}

	public Car setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Car setName(String name) {
		this.name = name;
		return this;
	}
	
	
}
