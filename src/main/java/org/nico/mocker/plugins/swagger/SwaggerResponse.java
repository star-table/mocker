package org.nico.mocker.plugins.swagger;

public class SwaggerResponse extends SwaggerSchema{
	
	protected SwaggerSchema schema;

	public SwaggerSchema getSchema() {
		return schema;
	}

	public void setSchema(SwaggerSchema schema) {
		this.schema = schema;
	}
	
}
