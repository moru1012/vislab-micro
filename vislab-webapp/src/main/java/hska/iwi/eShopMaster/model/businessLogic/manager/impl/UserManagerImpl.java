package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import de.hska.vislab.model.Role;
import de.hska.vislab.model.User;
import hska.iwi.eShopMaster.model.businessLogic.manager.UserManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.*;

/**
 * @author knad0001
 */
public class UserManagerImpl implements UserManager {
    private RestTemplate restTemplate;

    public UserManagerImpl() {
        restTemplate = new RestTemplate();
    }


    public void registerUser(String username, String name, String lastName, String password, Role role) {
        HttpEntity<User> request = new HttpEntity<>(new User(username, name, lastName, password, role));
//        restTemplate.exchange("http://localhost:9090/user-proxy-service/user/register", POST, request, Void.class);
        restTemplate.exchange("http://localhost:8088/user/register", POST, request, Void.class);
    }

    public User getUserByUsername(String username) {
        if (username == null || username.equals("")) {
            return null;
        }
//        User user = restTemplate.exchange("http://localhost:9090/user-proxy-service/user/{username}", GET, null, User.class, username).getBody();
        User user = restTemplate.exchange("http://localhost:8088/user/{username}", GET, null, User.class, username).getBody();
        return user;
    }

    public boolean deleteUserById(String id) {
//        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:9090/user-proxy-service/user/{userId}", DELETE, null, Void.class, id);
        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:8088/user/{userId}", DELETE, null, Void.class, id);
        return response.getStatusCode().equals(HttpStatus.OK);
    }

    public Role getRoleByLevel(int level) {
//        return restTemplate.exchange("http://localhost:9090/user-proxy-service/role/{level}", GET, null, Role.class, level).getBody();
        return restTemplate.exchange("http://localhost:8088/role/{level}", GET, null, Role.class, level).getBody();
    }

    public boolean doesUserAlreadyExist(String username) {
        return this.getUserByUsername(username) != null;
    }

    public boolean validate(User user) {
        return !user.getFirstName().isEmpty() && !user.getPassword().isEmpty() && user.getRole() != null && user.getLastName() != null && user.getUsername() != null;
    }

}
