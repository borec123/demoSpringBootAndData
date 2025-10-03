package com.borec.backend.pojo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.borec.backend.entity.Zprava;

class ZpravyResponseTest {

	@Test
	void test() {

		Zprava zprava = new Zprava();
		zprava.setCas_od(new Date());
		zprava.setCas_do(new Date());
		zprava.setZapnuto(true);
		zprava.setTitulek("Tavba 1");
		zprava.setZprava("Tavba 1 ...");

		List<Zprava> l1 = List.of(zprava);
		List<Zprava> l2 = List.of(zprava);
		ZpravyResponse a = new ZpravyResponse(l1);
		ZpravyResponse b = new ZpravyResponse(List.of());
		ZpravyResponse c = new ZpravyResponse(l2);

		assertTrue(l1.equals(l2));
		assertTrue(a.equals(a));
		assertTrue(a.equals(c));
		assertFalse(a.equals(b));
		assertFalse(c.equals(b));
	}
}
