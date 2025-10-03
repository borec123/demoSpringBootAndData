package com.borec.backend.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

class ZpravaTest {

	@Test
	void test() {
		Zprava zprava = new Zprava();
		zprava.setCas_od(new Date());
		zprava.setCas_do(new Date());
		zprava.setZapnuto(true);
		zprava.setTitulek("Tavba 1");
		zprava.setZprava("Tavba 1 ...");

		Zprava zprava3 = new Zprava();
		zprava3.setCas_od(new Date());
		zprava3.setCas_do(new Date());
		zprava3.setZapnuto(true);
		zprava3.setTitulek("Tavba 1");
		zprava3.setZprava("Tavba 1 ...");

		Zprava zprava2 = new Zprava();
		zprava2.setCas_od(new Date());
		zprava2.setCas_do(new Date());
		zprava2.setZapnuto(true);
		zprava2.setTitulek("Tavba 1 2");
		zprava2.setZprava("Tavba 1 ...  2");
		
		assertTrue(zprava.equals(zprava3));
		assertFalse(zprava.equals(zprava2));
	}

}
