package com.borec.backend.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Zprava {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	Long id;

	@Column(name = "cas_od", nullable = false)
	Date cas_od;

	@Column(name = "cas_do", nullable = false)
	Date cas_do;

	@Column(name = "zapnuto", nullable = false)
	Boolean zapnuto;

	@Column(name = "titulek", nullable = false)
	String titulek;

	@Column(name = "zprava", nullable = false)
	String zprava;

	@Override
	public String toString() {

		return getTitulek();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCas_od() {
		return cas_od;
	}

	public void setCas_od(Date cas_od) {
		this.cas_od = cas_od;
	}

	public Date getCas_do() {
		return cas_do;
	}

	public void setCas_do(Date cas_do) {
		this.cas_do = cas_do;
	}

	public Boolean getZapnuto() {
		return zapnuto;
	}

	public void setZapnuto(Boolean zapnuto) {
		this.zapnuto = zapnuto;
	}

	public String getTitulek() {
		return titulek;
	}

	public void setTitulek(String titulek) {
		this.titulek = titulek;
	}

	public String getZprava() {
		return zprava;
	}

	public void setZprava(String zprava) {
		this.zprava = zprava;
	}

}
