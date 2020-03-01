package org.nico.mocker.plugins.swagger;

import org.nico.mocker.enums.PluginPathType;
import org.nico.mocker.model.Plugin;

public class SwaggerPlugin extends Plugin{
	
	private String path;
	
	private PluginPathType pathType;
	
	private String version;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public PluginPathType getPathType() {
		return pathType;
	}

	public void setPathType(PluginPathType pathType) {
		this.pathType = pathType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}