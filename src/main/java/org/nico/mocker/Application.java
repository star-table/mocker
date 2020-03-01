package org.nico.mocker;

import org.apache.commons.lang3.ArrayUtils;
import org.nico.mocker.exception.MockerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
    	if(ArrayUtils.isEmpty(args)) {
    		throw new MockerException("The api document address is not set, example: java -jar mocker.jar http://host:port/docs");
    	}
    	String apiDoc = args[0];
    	ApiContainer.parseApis(apiDoc);
    	ApiContainer.autoRefresh(apiDoc);
        SpringApplication.run(Application.class, args);
    }
}