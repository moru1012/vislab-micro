package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import de.hska.vislab.model.Role;
import de.hska.vislab.model.User;
import hska.iwi.eShopMaster.model.businessLogic.manager.UserManager;
import java.util.Arrays;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.*;

/**
 * @author knad0001
 */
public class UserManagerImpl implements UserManager {
    private RestTemplate restTemplate;

    public UserManagerImpl() {
        OAuth2ClientContext clientContext = new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest());

        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setUsername("roy");
        resourceDetails.setPassword("spring");
        resourceDetails.setAccessTokenUri(" http://localhost:8081/auth/oauth/token");
        resourceDetails.setClientId("clientapp");
        resourceDetails.setClientSecret("123456");
        resourceDetails.setGrantType("authorization_code");
        resourceDetails.setScope(Arrays.asList("read", "write"));
        restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
    }


    public void registerUser(String username, String name, String lastName, String password, Role role) {
        HttpEntity<User> request = new HttpEntity<>(new User(username, name, lastName, password, role));
        restTemplate.exchange("http://gateway-service:9090/user-proxy-service/user/register", POST, request, Void.class);
    }

    public User getUserByUsername(String username) {
        if (username == null || username.equals("")) {
            return null;
        }
        User user = restTemplate.exchange("http://gateway-service:9090/user-proxy-service/user/{username}", GET, null, User.class, username).getBody();
        return user;
    }

    public boolean deleteUserById(String id) {
        ResponseEntity<Void> response = restTemplate.exchange("http://gateway-service:9090/user-proxy-service/user/{userId}", DELETE, null, Void.class, id);
        return response.getStatusCode().equals(HttpStatus.OK);
    }

    public Role getRoleByLevel(int level) {
        return restTemplate.exchange("http://gateway-service:9090/user-proxy-service/role/{level}", GET, null, Role.class, level).getBody();
    }

    public boolean doesUserAlreadyExist(String username) {
        return this.getUserByUsername(username) != null;
    }

    public boolean validate(User user) {
        return !user.getFirstName().isEmpty() && !user.getPassword().isEmpty() && user.getRole() != null && user.getLastName() != null && user.getUsername() != null;
    }

}
