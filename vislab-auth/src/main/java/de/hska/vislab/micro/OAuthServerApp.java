package de.hska.vislab.micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@ComponentScan(
    basePackages = {
        "de.hska.vislab.micro.controller",
        "de.hska.vislab.micro.configuration",
    }
)
@EnableResourceServer
public class OAuthServerApp {

  public static void main(String[] args) {
    SpringApplication.run(OAuthServerApp.class, args);
  }
}
