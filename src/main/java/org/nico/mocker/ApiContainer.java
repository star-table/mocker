package org.nico.mocker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nico.mocker.enums.PluginPathType;
import org.nico.mocker.model.Api;
import org.nico.mocker.plugins.AbstractPluginHandler;
import org.nico.mocker.plugins.swagger.SwaggerPlugin;
import org.nico.mocker.plugins.swagger.SwaggerPluginHandler;
import org.nico.mocker.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class ApiContainer {

	private static Logger log = LoggerFactory.getLogger(ApiContainer.class);
	
	private static List<Api> apis;
	
	private static AbstractPluginHandler handler = new SwaggerPluginHandler();
	
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
			synchronized (ApiContainer.class) {
				if(apis == null) {
					apis = new ArrayList<Api>();
				}
			}
		}
		return apis;
	}
	
	public static List<Api> parseApis(String apiDoc) throws Exception{
		log.info("Parse api doc {}", apiDoc);
		SwaggerPlugin sp = new SwaggerPlugin();
		sp.setName("swagger");
		sp.setPath(apiDoc);
		sp.setPathType(PluginPathType.HTTP);
		return handler.extract(sp);
	}
	
	public static void autoRefresh(String apiDoc) {
		Thread refreshThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(60 * 1000);
						log.info("Refresh api doc {}", apiDoc);
						apis = parseApis(apiDoc);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		refreshThread.setDaemon(true);
		refreshThread.start();
	}
}
