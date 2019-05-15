package com.epodSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;


@PropertySource("file:/opt/etrans/app_properties/epod_app.properties")
@SpringBootApplication
public class EPODApplication {

	public static void main(String[] args) {
		SpringApplication.run(EPODApplication.class, args);
	}

}

/*@EnableAutoConfiguration
@ComponentScan("com")
@PropertySource("file:/opt/etrans/app_properties/epod_app.properties")
//@SpringBootApplication(scanBasePackages={"com.EPOD.controller"})
@SpringBootApplication
public class EPODApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EPODApplication.class);
	}

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(EPODApplication.class)
				.run(args);

	}

}*/