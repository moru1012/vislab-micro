package de.hska.vislab.micro.configuration;

import de.hska.vislab.micro.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(-20)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Bean
  public static NoOpPasswordEncoder passwordEncoder() {
    return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
  }

  /*
   * Override this method to expose the AuthenticationManager from
   * configure(AuthenticationManagerBuilder) to be exposed as a Bean.
   *
   */
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
//    auth
//        .inMemoryAuthentication()
//        .withUser("user").password("password").roles("USER");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .requestMatchers().antMatchers("/oauth/authorize", "/oauth/confirm_access")
        .and()
        .authorizeRequests().anyRequest().authenticated();
  }
}
