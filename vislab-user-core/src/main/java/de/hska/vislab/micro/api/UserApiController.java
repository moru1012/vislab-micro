package de.hska.vislab.micro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hska.vislab.dbm.repositories.RoleRepository;
import de.hska.vislab.dbm.repositories.UserRepository;
import de.hska.vislab.model.Role;
import de.hska.vislab.model.User;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(
        value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-09T06:42:04.687Z"
)
@RestController
public class UserApiController implements UserApi {

    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    public UserApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public void mock() {
        List<User> all = userRepository.findAll();
        if (all.isEmpty()) {
            Role adminRole = new Role("admin", 0);
            roleRepository.save(adminRole);
            Role userRole = new Role("user", 1);
            roleRepository.save(userRole);

            User admin = new User("admin", "admin", "admin", "admin", adminRole);
            userRepository.save(admin);

            User user = new User("user", "Max", "Mustermann", "password", userRole);
            userRepository.save(user);
        }
    }

    public ResponseEntity<Void> createUser(
            @ApiParam(value = "Created user object", required = true) @Valid @RequestBody User body) {
        String accept = request.getHeader("Accept");

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

    public ResponseEntity<List<User>> getUsers() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<User> userList = userRepository.findAll();
            if (userList != null) {
                return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
            }
        }
        return new ResponseEntity<List<User>>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<User> getUserByUserName(
            @Min(1)
            @ApiParam(value = "Username of the user that needs to be found", required = true)
            @PathVariable("username")
                    String username) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            mock();
            User user = null;
            if (userRepository.existsByUsername(username)) {
                user = userRepository.findByUsername(username);;
            }
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> deleteById(
            @Min(1)
            @ApiParam(value = "ID of the user that needs to be deleted", required = true)
            @PathVariable("userId")
                    String userId) {
        String accept = request.getHeader("Accept");
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }
}
