package de.hska.vislab.micro.service;

import static org.springframework.http.HttpMethod.GET;

import de.hska.vislab.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;

public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = restTemplate.exchange("http://user-proxy:8088/user/{username}", GET, null, User.class, username).getBody();
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return new CustomUserPrincipal(user);
  }
}