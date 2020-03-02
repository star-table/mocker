package org.nico.mocker.plugins.swagger;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SwaggerSchema {

	private String originalRef;
	
	@JSONField(name = "$ref")
	@SerializedName("$ref")
	private String ref;
	
	private String type;
	
	private SwaggerSchema additionalProperties;
	
	private SwaggerSchema items;

	public SwaggerSchema(String ref) {
		this.ref = ref;
	}

	public SwaggerSchema() {
	}
}
