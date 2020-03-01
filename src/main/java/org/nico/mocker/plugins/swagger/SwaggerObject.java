package org.nico.mocker.plugins.swagger;

import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class SwaggerObject {

	private String type;
	
	private String title;
	
	private String format;
	
	private String description;
	
	private Map<String, SwaggerObject> properties;
	
	private SwaggerSchema additionalProperties;
	
	private SwaggerSchema items;
	
	private String originalRef;
	
	private boolean required;
	
	@JSONField(name = "$ref")
	private String ref;
	
}
