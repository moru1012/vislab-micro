package de.hska.vislab.micro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hska.vislab.dbm.MongoConfigurationManager;
import de.hska.vislab.dbm.repositories.UserRepository;
import de.hska.vislab.model.User;
import io.swagger.annotations.ApiParam;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@javax.annotation.Generated(
  value = "io.swagger.codegen.languages.SpringCodegen",
  date = "2018-05-09T06:42:04.687Z"
)
@Controller
public class UserApiController implements UserApi {

  private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

  private final ObjectMapper objectMapper;

  private final HttpServletRequest request;

  @org.springframework.beans.factory.annotation.Autowired
  public UserApiController(ObjectMapper objectMapper, HttpServletRequest request) {
    this.objectMapper = objectMapper;
    this.request = request;
  }

  public ResponseEntity<Void> createUser(
      @ApiParam(value = "Created user object", required = true) @Valid @RequestBody User body) {
    String accept = request.getHeader("Accept");

    UserRepository userRepository =
        MongoConfigurationManager.getInstance().getBean(UserRepository.class);
    User newUser =
        new User(
            body.getUsername(),
            body.getFirstName(),
            body.getLastName(),
            body.getPassword(),
            body.getRole());
    userRepository.save(newUser);

    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  public ResponseEntity<String> loginUser(
      @NotNull
          @ApiParam(value = "The user name for login", required = true)
          @Valid
          @RequestParam(value = "username", required = true)
          String username,
      @NotNull
          @ApiParam(value = "The password for login in clear text", required = true)
          @Valid
          @RequestParam(value = "password", required = true)
          String password) {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/json")) {
      UserRepository userRepository =
          MongoConfigurationManager.getInstance().getBean(UserRepository.class);
      User user = userRepository.findByUsernameAndPassword(username, password);
      if (user != null) {
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
      }
    }
    return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<Void> logoutUser() {
    String accept = request.getHeader("Accept");

    return new ResponseEntity<Void>(HttpStatus.OK);
  }
}
