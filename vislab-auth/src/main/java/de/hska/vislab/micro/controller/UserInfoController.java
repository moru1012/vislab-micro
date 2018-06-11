package de.hska.vislab.micro.controller;

import java.security.Principal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {
  @RequestMapping(value = "/user")
  public Principal userInfo(@AuthenticationPrincipal Principal user) {
    return user;
  }
}
