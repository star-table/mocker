package org.nico.mocker;

import org.apache.commons.lang3.ArrayUtils;
import org.nico.mocker.container.ApiContainer;
import org.nico.mocker.container.DataContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
    	if(ArrayUtils.isEmpty(args)) {
    		System.out.println("Error: The api document address is not set, example: java -jar mocker.jar http://host:port/docs");
    		return;
    	}
    	String docsApi = args[0];
    	ApiContainer.enable(docsApi);
    	ApiContainer.parseApis();
    	ApiContainer.autoRefresh(60 * 1000L);
    	
    	if(args.length > 1) {
    		String dataRepository = args[1];
    		DataContainer.enable(dataRepository);
    		DataContainer.autoRefresh(60 * 1000L);
    	}
        SpringApplication.run(Application.class, args);
    }
}