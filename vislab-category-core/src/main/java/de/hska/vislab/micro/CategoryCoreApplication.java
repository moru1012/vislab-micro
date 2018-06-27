package de.hska.vislab.micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@ComponentScan(
    basePackages = {
        "de.hska.vislab.micro.api",
        "de.hska.vislab.model",
        "de.hska.vislab.micro.configuration",
        "de.hska.vislab.dbm"
    }
)
public class CategoryCoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(CategoryCoreApplication.class, args);
  }

}