package org.nico.mocker.plugins.swagger;

import lombok.Data;

@Data
public class SwaggerResponse {

	private String in;
	
	private String description;
	
	private SwaggerSchema schema;
}
