package org.nico.mocker.plugins.swagger;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SwaggerResponse {

	private String in;
	
	private String description;
	
	private SwaggerSchema schema;
	
	@JSONField(name = "$ref")
	@SerializedName("$ref")
	private String ref;
}
