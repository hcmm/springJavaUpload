package com.hmkcode.spring.mvc.model;

import java.util.Collection;
import java.util.LinkedList;

public class Correspondencia {
	
	private Collection<FileMeta> anexos = new LinkedList<FileMeta>();

	public Collection<FileMeta> getAnexos() {
		return anexos;
	}

	public void setAnexos(Collection<FileMeta> anexos) {
		this.anexos = anexos;
	}
	
	

}
