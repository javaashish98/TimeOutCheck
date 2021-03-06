package com.copart;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	//smoothly initilized
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(InterThreadCommunicationProjectApplication.class);
	}

}
