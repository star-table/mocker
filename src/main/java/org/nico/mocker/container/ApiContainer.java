package org.nico.mocker.container;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nico.mocker.enums.PluginPathType;
import org.nico.mocker.model.Api;
import org.nico.mocker.plugins.AbstractPluginHandler;
import org.nico.mocker.plugins.swagger.SwaggerPlugin;
import org.nico.mocker.plugins.swagger.SwaggerPluginHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

public class ApiContainer {

	private static Logger log = LoggerFactory.getLogger(ApiContainer.class);
	
	private static List<Api> apis;
	
	public static String docsAPi;
	
	private static AbstractPluginHandler handler = new SwaggerPluginHandler();
	
	private static PathMatcher antMatcher = new AntPathMatcher();
	
	public static String enable(String docsAPi) {
		ApiContainer.docsAPi = docsAPi;
		log.info("Docs api {}", docsAPi);
		return ApiContainer.docsAPi;
	}
	
	public static boolean isEnable() {
		return StringUtils.isNotBlank(docsAPi);
	}
	
	public static Api getApi(String apiPath, String httpMethod) throws Exception {
		List<Api> apis = getApis();
		if(! CollectionUtils.isEmpty(apis)) {
			for(Api api: apis) {
				if(api.getMethod().toString().equalsIgnoreCase(httpMethod) && antMatcher.match(api.getPath(), apiPath)) {
					return api;
				}
			};
		}
		return null;
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
