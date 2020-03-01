package org.nico.mocker.plugins.swagger;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SwaggerPath {

	private List<String> tags;
	
	private String summary;
	
	private String description;
	
	private List<String> consumes;
	
	private List<String> produces;
	
	private List<SwaggerParameter> parameters;
	
	private Map<String, SwaggerResponse> responses;
	
	private boolean deprecated;
	
}
