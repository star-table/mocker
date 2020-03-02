package org.nico.mocker.enums;

public enum HttpMethod {

	GET,
	
	HEAD,
	
	POST,
	
	PUT,
	
	DELETE,
	
	CONNECT,
	
	OPTIONS,
	
	TRACE,
	
	PATCH
	
	;
	
	public static HttpMethod parse(String m) {
		HttpMethod method = null;
		if(m != null) {
			method = HttpMethod.valueOf(m.toUpperCase());
		}
		if(method == null) {
			method = GET;
		}
		return method;
	}
}
