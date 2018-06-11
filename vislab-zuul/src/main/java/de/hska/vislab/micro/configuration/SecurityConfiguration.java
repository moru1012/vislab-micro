package de.hska.vislab.micro.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(-20)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .requestMatchers().antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access")
        .and()
        .formLogin().loginPage("/login").permitAll().failureUrl("/login?error")
        .and()
        .authorizeRequests().anyRequest().authenticated();
  }
}
