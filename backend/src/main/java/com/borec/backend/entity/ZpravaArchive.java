package com.borec.backend.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "ZPRAVA_ARCHIVE")
public class ZpravaArchive extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
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

	@Column(name = "smazano", nullable = false)
	String smazano;

	public ZpravaArchive(Zprava zprava, ZpravaArchiveCreated created) {
		this.id = zprava.id;
		this.cas_od = zprava.cas_od;
		this.cas_do = zprava.cas_do;
		this.zapnuto = zprava.zapnuto;
		this.titulek = zprava.titulek;
		this.zprava = zprava.zprava;
		this.smazano = created.toString();
	}
	
	public Long getId() {
		return id;
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

	public String getSmazano() {
		return smazano;
	}

	public void setSmazano(String smazano) {
		this.smazano = smazano;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
