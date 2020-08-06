package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * This class provides a setting of default MediaType.
 *
 * To switch to XML MediaType simply put a line in @WebConfig.configureContentNegotiation method:
 * 
 * configurer.defaultContentType(MediaType.APPLICATION_XML);
 * 
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	/**
	 * This method provides a setting of default MediaType.
	 *
	 * To switch to XML MediaType simply put a line:
	 * 
	 * configurer.defaultContentType(MediaType.APPLICATION_XML);
	 * 
	 */
	  @Override
	  public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
		    configurer.defaultContentType(MediaType.APPLICATION_JSON);
		    //configurer.defaultContentType(MediaType.APPLICATION_XML);
	  }
}
