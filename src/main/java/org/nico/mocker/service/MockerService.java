package org.nico.mocker.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nico.mocker.enums.ApiParameterType;
import org.nico.mocker.model.Api;
import org.nico.mocker.model.ApiParameter;
import org.nico.mocker.random.AbstractRandom;
import org.nico.mocker.utils.HttpContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MockerService {

	@Autowired
	private AbstractRandom random;
	
	public Object rendering(Api api) {
		ApiParameter sucResponse = api.getResponses().get("200");
		if(sucResponse != null) {
			return mock(sucResponse);
		}
		return null;
	}
	
	private Object mock(ApiParameter parameter) {
		switch (parameter.getType()) {
		case MAP:
			Map<String, Object> data = new HashMap<String, Object>();
			
			ApiParameter value = parameter.getExtra();
			if(value == null) {
				value = ApiParameter.RANDOM;
			}
			for(int i = 0; i < HttpContextUtils.getMapSize(); i ++) {
				data.put(random.randomKey() + i, mock(value));
			}
			return data;
		case ARRAY:
			List<Object> list = new ArrayList<Object>();
			for(int i = 0; i < HttpContextUtils.getListSize(); i ++) {
				list.add(mock(parameter.getExtra()));
			}
			return list;
		case STRING:
			return random.randomString();
		case INT:
			return random.randomInteger();
		case LONG:
			return random.randomLong();
		case DATE:
			return random.randomDate();
		case FLOAT:
			return random.randomDouble();
		case BOOLEAN:
			return random.randomBoolean();
		case OBJECT:
			if(! CollectionUtils.isEmpty(parameter.getFields())) {
				data = new HashMap<String, Object>();
				parameter.getFields().forEach((k, v) -> {
					data.put(k, mock(v));
				});
				return data;
			}else {
				return mock(new ApiParameter(ApiParameterType.MAP));
			}
		case RANDOM:
			return random.random();
		default:
			return mock(ApiParameter.RANDOM);
		}
	}
}
