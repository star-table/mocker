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
	
	private static String[] sucHttpCodes = new String[] {"200", "201", "202", "203", "204", "205", "206"};
	
	public Object rendering(Api api) {
		for(String httpCode: sucHttpCodes) {
			ApiParameter sucResponse = api.getResponses().get(httpCode);
			if(sucResponse != null) {
				Map<String, Integer> cycleRecords = new HashMap<>();
				return mock(sucResponse, cycleRecords);
			}	
		}
		return null;
	}
	
	private Object mock(ApiParameter parameter, Map<String, Integer> cycleRecords) {
		switch (parameter.getType()) {
		case MAP:
			Map<String, Object> data = new HashMap<String, Object>();
			
			ApiParameter value = parameter.getExtra();
			if(value == null) {
				value = ApiParameter.RANDOM;
			}
			for(int i = 0; i < HttpContextUtils.getMapSize(); i ++) {
				data.put(random.randomKey() + i, mock(value, cycleRecords));
			}
			return data;
		case ARRAY:
			List<Object> list = new ArrayList<Object>();
			for(int i = 0; i < HttpContextUtils.getListSize(); i ++) {
				list.add(mock(parameter.getExtra(), cycleRecords));
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
				if(cycleRecord(cycleRecords, parameter.getName())) {
					parameter.getFields().forEach((k, v) -> {
						data.put(k, mock(v, cycleRecords));
					});
					cycleCancelRecord(cycleRecords, parameter.getName());
				}
				return data;
			}else {
				return mock(new ApiParameter(ApiParameterType.MAP), cycleRecords);
			}
		case RANDOM:
			return random.random();
		default:
			return mock(ApiParameter.RANDOM, cycleRecords);
		}
	}
	
	private boolean cycleRecord(Map<String, Integer> cycleRecords, String name) {
		Integer time = cycleRecords.get(name);
		if(time == null) {
			time = 0;
		}
		if(time > HttpContextUtils.getCycleSize()) {
			return false;
		}
		
		time ++;
		cycleRecords.put(name, time);
		return true;
	}
	
	private void cycleCancelRecord(Map<String, Integer> cycleRecords, String name) {
		Integer time = cycleRecords.get(name);
		if(time != null && time > 0) {
			time --;
			cycleRecords.put(name, time);
		}
	}
}
