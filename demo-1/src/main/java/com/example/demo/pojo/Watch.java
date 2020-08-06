package com.example.demo.pojo;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement (name = "Watch")
@XmlAccessorType(XmlAccessType.NONE)
public class Watch {

    private Long id;
    
    @XmlElement
    private String title;

    @XmlElement
    private int price;

    @XmlElement
    private String description;

    @XmlElement
    private String fountain;

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

	public Watch setTitle(String title) {
		this.title = title;
		return this;
	}

	public int getPrice() {
		return price;
	}

	public Watch setPrice(int price) {
		this.price = price;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Watch setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getFountain() {
		return fountain;
	}

	public Watch setFountain(String fountain) {
		this.fountain = fountain;
		return this;
	}
    
    

   
}
