package org.nico.mocker.container;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
	
	public static String docsAPi;
	
	private static AbstractPluginHandler handler = new SwaggerPluginHandler();
	
	public static String enable(String docsAPi) {
		ApiContainer.docsAPi = docsAPi;
		log.info("Docs api {}", docsAPi);
		return ApiContainer.docsAPi;
	}
	
	public static boolean isEnable() {
		return StringUtils.isNotBlank(docsAPi);
	}
	
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
	
	public static List<Api> parseApis() throws Exception{
		log.info("Parse api doc {}", docsAPi);
		SwaggerPlugin sp = new SwaggerPlugin();
		sp.setName("swagger");
		sp.setPath(docsAPi);
		sp.setPathType(PluginPathType.HTTP);
		ApiContainer.apis = handler.extract(sp);
		return ApiContainer.apis;
	}
	
	public static void autoRefresh(Long interval) {
		Thread refreshThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(interval);
						log.info("Refresh api doc {}", docsAPi);
						parseApis();
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
