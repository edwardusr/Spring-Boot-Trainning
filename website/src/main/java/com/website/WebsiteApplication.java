package com.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Created by smatt on 21/08/2017.
 */


//@SpringBootApplication
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WebsiteApplication {
	
	public static void main(String[] args) {
		
		
		
		SpringApplication.run(WebsiteApplication.class, args);
		
		// this works
        // export SPRING_PROFILES_ACTIVE=dev  
        // System.setProperty("spring.profiles.active", "dev"); // so does this
//        new SpringApplicationBuilder()
//            //.profiles("dev") // and so does this
//            .sources(WebsiteApplication.class)
//            .initializers(context -> context
//                    .getEnvironment()
//                    .getPropertySources()
//                    
//                )
//            .run(args);
	}
	
	


}
