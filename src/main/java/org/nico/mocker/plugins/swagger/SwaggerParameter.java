package org.nico.mocker.plugins.swagger;

import lombok.Data;

@Data
public class SwaggerParameter {

	private String name;
	
	private String in;
	
	private String description;
	
	private boolean required;
	
	private String type;
	
	private SwaggerSchema items;
}
