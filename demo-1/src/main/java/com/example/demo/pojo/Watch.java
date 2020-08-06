package com.example.demo.pojo;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "Watch")
@XmlAccessorType(XmlAccessType.NONE)
public class Watch {

    private Long id;
    
    @NotNull
    @XmlElement
    private String title;

    @NotNull
    @DecimalMin("0.01")
    @XmlElement
    private BigDecimal price;

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

	public BigDecimal getPrice() {
		return price;
	}

	public Watch setPrice(BigDecimal price) {
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
