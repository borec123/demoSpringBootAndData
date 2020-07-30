package com.example.demo.model;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.engine.jdbc.BlobProxy;

@Entity
public class Watch extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id")
    private Long id;

	@Column(name="title", nullable=false, length=255)
    private String title;

	@Column(name="price", nullable=false)
    private int price;

	@Column(name="description", nullable=true, length=2048)
    private String description;

	@Column(name="fountain", nullable=true)
    private Blob fountain;

	@Transient
    private String fountainString;

	public Long getId() {
		return id;
	}

	public Watch setId(Long id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Watch setTitle(String name) {
		this.title = name;
		return this;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFountainString() {
		if(fountain != null  ) {
			try {
				return (Base64.getEncoder().encodeToString(fountain.getBytes(0, (int)fountain.length())));
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		else {
			return fountainString;
		}
	}

	public void setFountainString(String fountainString) {
		this.fountainString = fountainString;
		byte[] decodedBytes = Base64.getDecoder().decode(fountainString);
		fountain = BlobProxy.generateProxy(decodedBytes);
	}

	/*
	 * public Blob getFountain() { return fountain; }
	 * 
	 * public void setFountain(Blob fountain) { this.fountain = fountain; }
	 */
	
	
}
