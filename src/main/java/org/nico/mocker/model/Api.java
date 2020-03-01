package org.nico.mocker.model;

import java.util.List;
import java.util.Map;

import org.nico.mocker.enums.HttpMethod;

import lombok.Data;

@Data
public class Api {

	/**
	 * Path.
	 */
	private String path;
	
	/**
	 * Api desc.
	 */
	private String desc;
	
	/**
	 * Api http type.
	 */
	private HttpMethod method;
	
	/**
	 * Api headers.
	 */
	private List<ApiHeader> headers;
	
	/**
	 * Api request query parameters.
	 */
	private Map<String, ApiParameter> queryParameters;
	
	/**
	 * Api request form parameters. 
	 */
	private Map<String, ApiParameter> formParameters;
	
	/**
	 * Api request body.
	 */
	private ApiParameter body;
	
	/**
	 * Api response data.
	 */
	private Map<String, ApiParameter> responses;
}
