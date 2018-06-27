package de.hska.vislab.micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@RibbonClient("auth-server")
@ComponentScan(
    basePackages = {
        "de.hska.vislab.micro.controller",
        "de.hska.vislab.micro.configuration",
        "de.hska.vislab.micro.service",
        "de.hska.vislab.dbm"
    }
)
public class OAuthServerApp {

  public static void main(String[] args) {
    SpringApplication.run(OAuthServerApp.class, args);
  }
}
