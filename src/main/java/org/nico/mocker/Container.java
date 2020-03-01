package org.nico.mocker;

import java.util.List;
import java.util.Map;

import org.nico.mocker.enums.PluginPathType;
import org.nico.mocker.model.Api;
import org.nico.mocker.plugins.AbstractPluginHandler;
import org.nico.mocker.plugins.swagger.SwaggerPlugin;
import org.nico.mocker.plugins.swagger.SwaggerPluginHandler;
import org.nico.mocker.utils.MapUtils;
import org.springframework.util.CollectionUtils;

public class Container {

	private static List<Api> apis;
	
	public static String apiDoc;
	
	public static Api getApi(String api) throws Exception {
		Map<String, Api> apiMap = getApiMap();
		if(! CollectionUtils.isEmpty(apiMap)) {
			return apiMap.get(api);
		}
		return null;
		
	}
	
	public static Map<String, Api> getApiMap() throws Exception {
		List<Api> apis = getApis();
		return MapUtils.toMap(Api::getPath, apis);
		
	}
	
	public static List<Api> getApis() throws Exception{
		if(apis == null) {
			synchronized (Container.class) {
				if(apis == null) {
					SwaggerPlugin sp = new SwaggerPlugin();
					sp.setName("swagger");
					sp.setPath(apiDoc);
					sp.setPathType(PluginPathType.HTTP);
					AbstractPluginHandler handler = new SwaggerPluginHandler();
					apis = handler.extract(sp);
				}
			}
		}
		return apis;
	}
}
