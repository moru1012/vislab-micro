package de.hska.vislab.micro.service;

import de.hska.vislab.model.Role;
import de.hska.vislab.model.User;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserPrincipal implements UserDetails {

  private User user;

  public CustomUserPrincipal(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    final Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

    Role role = null;

    if (user != null) {
      role = user.getRole();
    }

    if (role != null) {
      if (role.getType().equals("admin")) {
        grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
      } else {
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
      }
    }
    return grantedAuthorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return user != null;
  }

  @Override
  public boolean isAccountNonLocked() {
    return user != null;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return user != null;
  }

  @Override
  public boolean isEnabled() {
    return user != null;
  }
}
