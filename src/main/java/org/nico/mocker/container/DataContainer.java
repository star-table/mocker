package org.nico.mocker.container;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.nico.mocker.utils.FileUtils;
import org.nico.mocker.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataContainer {

	private static Logger log = LoggerFactory.getLogger(DataContainer.class);
	
	private static Map<String, String> dataCacher = new HashMap<String, String>();
	
	public static String dataRepository;
	
	public static String enable(String dataRepository) {
		DataContainer.dataRepository = dataRepository;
		log.info("Data repository {}", dataRepository);
		return DataContainer.dataRepository;
	}
	
	public static boolean isEnable() {
		return StringUtils.isNotBlank(dataRepository);
	}
	
	public static String getMockData(String path, String version) {
		String key = assemblyCacheKey(path, version);
		if(dataCacher.containsKey(key)) {
			return dataCacher.get(key);
		}
		if(StringUtils.isNotBlank(dataRepository)) {
			String mockDataUri = FileUtils.combination(dataRepository, key);
			log.info("get mock data from: {}", mockDataUri);
			String mockData = HttpUtils.sendGet(mockDataUri);
			dataCacher.put(key, mockData);
			return mockData;
		}
		return "";
	}
	
	private static String assemblyCacheKey(String path, String version) {
		String key = "";
		if(StringUtils.isNotBlank(path)) {
			key += path;
		}
		if(StringUtils.isBlank(version)) {
			version = "data";
		}
		version += ".json";
		return key + "/" + version;
	}
	
	public static void autoRefresh(Long interval) {
		Thread refreshThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(interval);
						log.info("Clear mock data cache.");
						dataCacher.clear();
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
