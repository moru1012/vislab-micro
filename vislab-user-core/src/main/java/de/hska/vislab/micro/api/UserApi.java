/**
 * NOTE: This class is auto generated by the swagger code generator program (2.3.1).
 * https://github.com/swagger-api/swagger-codegen Do not edit the class manually.
 */
package de.hska.vislab.micro.api;

import de.hska.vislab.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@javax.annotation.Generated(
  value = "io.swagger.codegen.languages.SpringCodegen",
  date = "2018-05-09T06:42:04.687Z"
)
@Api(value = "user", description = "the user API")
public interface UserApi {

  @ApiOperation(
    value = "Register user",
    nickname = "createUser",
    notes = "",
    tags = {
      "user",
    }
  )
  @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation")})
  @RequestMapping(
    value = "/user/register",
    produces = {"application/json"},
    method = RequestMethod.POST
  )
  ResponseEntity<Void> createUser(
      @ApiParam(value = "Created user object", required = true) @Valid @RequestBody User body);

  @ApiOperation(
    value = "Logs user into the system",
    nickname = "loginUser",
    notes = "",
    response = String.class,
    tags = {
      "user",
    }
  )
  @ApiResponses(
    value = {
      @ApiResponse(code = 200, message = "successful operation", response = String.class),
      @ApiResponse(code = 400, message = "Invalid username/password supplied")
    }
  )
  @RequestMapping(
    value = "/user/login",
    produces = {"application/json"},
    method = RequestMethod.GET
  )
  ResponseEntity<String> loginUser(
      @NotNull
          @ApiParam(value = "The user name for login", required = true)
          @Valid
          @RequestParam(value = "username", required = true)
          String username,
      @NotNull
          @ApiParam(value = "The password for login in clear text", required = true)
          @Valid
          @RequestParam(value = "password", required = true)
          String password);

  @ApiOperation(
    value = "Logs out current logged in user session",
    nickname = "logoutUser",
    notes = "",
    tags = {
      "user",
    }
  )
  @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation")})
  @RequestMapping(
    value = "/user/logout",
    produces = {"application/json"},
    method = RequestMethod.GET
  )
  ResponseEntity<Void> logoutUser();
}
