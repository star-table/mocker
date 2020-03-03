package org.nico.mocker.plugins.swagger;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SwaggerApi {

	private String swagger;
	
	private String basePath;
	
	private String host;
	
	private List<SwaggerTag> tags;
	
	private Map<String, Map<String, SwaggerPath>> paths; 
	
	private Map<String, SwaggerObject> definitions;
	
	private Map<String, SwaggerResponse> responses;
	
}
