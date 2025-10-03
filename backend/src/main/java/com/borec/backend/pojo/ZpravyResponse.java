package com.borec.backend.pojo;

import java.util.List;

import com.borec.backend.entity.Zprava;

public class ZpravyResponse {
	List<Zprava> list;

	public ZpravyResponse() { }
	
	public ZpravyResponse(List<Zprava> list) {
		super();
		this.list = list;
	}

	public List<Zprava> getList() {
		return list;
	}

	@Override
	public int hashCode() {
		return list.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return list.equals(((ZpravyResponse)obj).list);
	}
}
