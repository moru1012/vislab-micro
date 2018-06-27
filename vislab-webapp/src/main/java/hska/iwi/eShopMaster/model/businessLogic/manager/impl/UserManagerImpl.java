package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import de.hska.vislab.model.Role;
import de.hska.vislab.model.User;
import hska.iwi.eShopMaster.model.businessLogic.manager.UserManager;
import java.util.Arrays;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
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

    public UserManagerImpl(String username, String password) {
        String tokenUri = "http://localhost:8081/auth/oauth/token";

        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setUsername(username);
        resourceDetails.setPassword(password);
        resourceDetails.setAccessTokenUri(tokenUri);
        resourceDetails.setClientId("vis");
        resourceDetails.setClientSecret("vissecret");
        resourceDetails.setGrantType("password");
        resourceDetails.setScope(Arrays.asList("read", "write"));

        OAuth2ClientContext clientContext = new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest());

        restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
    }


    public void registerUser(String username, String name, String lastName, String password, Role role) {
        HttpEntity<User> request = new HttpEntity<>(new User(username, name, lastName, password, role));
        restTemplate.exchange("http://localhost:9090/user-proxy-service/user/register", POST, request, Void.class);
    }

    public User getUserByUsername(String username) {
        if (username == null || username.equals("")) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ((OAuth2RestTemplate) restTemplate).getAccessToken().getValue());
        User user = restTemplate.exchange("http://localhost:9090/user-proxy-service/user/{username}", GET, new HttpEntity<>(null, headers), User.class, username).getBody();
        return user;
    }

    public boolean deleteUserById(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ((OAuth2RestTemplate) restTemplate).getAccessToken().getValue());
        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:9090/user-proxy-service/user/{userId}", DELETE, new HttpEntity<>(null, headers), Void.class, id);
        return response.getStatusCode().equals(HttpStatus.OK);
    }

    public Role getRoleByLevel(int level) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ((OAuth2RestTemplate) restTemplate).getAccessToken().getValue());
        return restTemplate.exchange("http://localhost:9090/user-proxy-service/role/{level}", GET, new HttpEntity<>(null, headers), Role.class, level).getBody();
    }

    public boolean doesUserAlreadyExist(String username) {
        return this.getUserByUsername(username) != null;
    }

    public boolean validate(User user) {
        return !user.getFirstName().isEmpty() && !user.getPassword().isEmpty() && user.getRole() != null && user.getLastName() != null && user.getUsername() != null;
    }

}
