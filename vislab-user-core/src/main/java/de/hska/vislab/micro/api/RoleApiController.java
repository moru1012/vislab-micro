package de.hska.vislab.micro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hska.vislab.dbm.repositories.RoleRepository;
import de.hska.vislab.model.Role;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;

@javax.annotation.Generated(
        value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-09T06:42:04.687Z"
)
@RestController
public class RoleApiController implements RoleApi {

    private static final Logger log = LoggerFactory.getLogger(RoleApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public RoleApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Role> getRoleByLevel(
            @Min(1)
            @ApiParam(value = "Level of the role that needs to be found", required = true)
            @PathVariable(value = "level", required = true)
                    int level) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Role role = roleRepository.findByLevel(level);
            if (role != null) {
                return new ResponseEntity<Role>(role, HttpStatus.OK);
            }
        }
        return new ResponseEntity<Role>(HttpStatus.BAD_REQUEST);
    }
}
