package org.nico.mocker.plugins.swagger;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SwaggerSchema {

	protected String originalRef;
	
	@JSONField(name = "$ref")
	@SerializedName("$ref")
	protected String ref;
	
	protected String type;
	
	protected SwaggerSchema additionalProperties;
	
	protected SwaggerSchema items;

	public SwaggerSchema(String ref) {
		this.ref = ref;
	}

	public SwaggerSchema() {
	}
}
