package de.hska.vislab.micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = { "de.hska.vislab.micro.api", "de.hska.vislab.model", "de.hska.vislab.micro.configuration"})
public class ProductCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductCoreApplication.class, args);
	}
}
