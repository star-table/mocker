package org.nico.mocker.model;

import lombok.Data;

@Data
public class ApiHeader {

	private String name;
	
	private boolean required;

	public ApiHeader(String name, boolean required) {
		this.name = name;
		this.required = required;
	}

	public ApiHeader() {
	}
	
}
