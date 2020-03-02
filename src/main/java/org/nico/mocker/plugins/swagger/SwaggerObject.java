package org.nico.mocker.plugins.swagger;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;

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
	
	private List<String> required;
	
	@JSONField(name = "$ref")
	@SerializedName("$ref")
	private String ref;
	
}
