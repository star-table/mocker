package org.nico.mocker.plugins.swagger;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class SwaggerSchema {

	private String originalRef;
	
	@JSONField(name = "$ref")
	private String ref;
	
	private String type;
	
	private SwaggerSchema additionalProperties;
	
	private SwaggerSchema items;
	
}
