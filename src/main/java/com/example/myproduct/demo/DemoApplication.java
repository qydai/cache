package com.example.myproduct.demo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication(
		scanBasePackages = "com.example")
@EnableAutoConfiguration
public class DemoApplication implements EmbeddedServletContainerCustomizer{

	public static void main(String[] args) {
		SpringApplicationBuilder application = new SpringApplicationBuilder();
		application.sources(DemoApplication.class);
		application.run(args);
	}

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(8083);
	}
}
