package com.almundo.automation.entities;

import java.util.List;

public class Geonames {
	private List<Local> local;

	public Geonames(List<Local> local) {
		this.local = local;
	}

	public List<Local> getLocal() {
		return local;
	}

	public void setLocal(List<Local> local) {
		this.local = local;
	}
}
