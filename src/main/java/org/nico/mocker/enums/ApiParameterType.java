package org.nico.mocker.enums;

import java.util.HashMap;
import java.util.Map;

public enum ApiParameterType {

	STRING,
	
	INT,
	
	LONG,
	
	FLOAT,
	
	DATE,
	
	BOOLEAN,
	
	OBJECT,
	
	ARRAY,
	
	FILE,
	
	MAP,
	
	RANDOM,
	;
	
	public static ApiParameterType get(String type) {
		if(type != null) {
			type = type.toLowerCase();
		}
		if(TYPE_MAP.containsKey(type)) {
			return TYPE_MAP.get(type);
		}
		return OBJECT;
	}
	
	public static final Map<String, ApiParameterType> TYPE_MAP = new HashMap<String, ApiParameterType>(){
		private static final long serialVersionUID = 1L;
		{
			put("string", STRING);
			
			put("boolean", BOOLEAN);
			
			put("array", ARRAY);
			
			put("number", FLOAT);
			put("double", FLOAT);
			put("float", FLOAT);
			
			put("int", INT);
			put("integer", INT);
			
			put("long", LONG);
			put("file", FILE);
			
			
			put("date", DATE);
			put("time", DATE);
			
			put("object", OBJECT);
			put("", OBJECT);
			put(null, OBJECT);
		}
	};
	
}
