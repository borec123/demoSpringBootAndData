package com.example.demo.entity;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.engine.jdbc.BlobProxy;

@Entity
public class WatchEntity extends BaseEntity<Long> {

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

	
	/*
	 * @Column(name="fountain", nullable=true)
	 * 
	 * @Lob private Blob fountain;
	 */	 
	
	
	@Column(name="fountain", nullable=true) 
	@Lob
	private String fountain;
	 
	
	
	public Long getId() {
		return id;
	}

	public WatchEntity setId(Long id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public WatchEntity setTitle(String name) {
		this.title = name;
		return this;
	}

	public int getPrice() {
		return price;
	}

	public WatchEntity setPrice(int price) {
		this.price = price;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public WatchEntity setDescription(String description) {
		this.description = description;
		return this;
	}

	
	  public String getFountain() { return fountain; }
	  
	  public WatchEntity setFountain(String fountain) { 
		  this.fountain = fountain;
		  return this; 
	  }
	 

	
	
	
	/*
	 * public String getFountainString() { return fountain;
	 * 
	 * if(fountain != null ) { try { return
	 * (Base64.getEncoder().encodeToString(fountain.getBytes(0,
	 * (int)fountain.length()))); } catch (SQLException e) { throw new
	 * RuntimeException(e); } } else { return fountainString; }
	 * 
	 * }
	 * 
	 * public void setFountainString(String fountainString) { this.fountain =
	 * fountainString;
	 * 
	 * byte[] decodedBytes = Base64.getDecoder().decode(fountainString); fountain =
	 * BlobProxy.generateProxy(decodedBytes);
	 * 
	 * }
	 */
	
	/*
	 * public Blob getFountain() { return fountain; }
	 * 
	 * public WatchEntity setFountain(Blob fountain) { this.fountain = fountain;
	 * return this; }
	 */	 
	
	
}
