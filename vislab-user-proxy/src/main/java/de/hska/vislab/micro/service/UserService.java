package de.hska.vislab.micro.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import de.hska.vislab.model.Category;
import de.hska.vislab.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Service
public class UserService {
    private final RestTemplate restTemplate;

    private final Map<String, User> userCache = new LinkedHashMap<>();

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(
            fallbackMethod = "getUserCache",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
            }
    )
    public User getUser(String username) {
        User user = restTemplate.exchange("http://user-service/user/{username}", GET, null, User.class, username).getBody();
        if (user != null) {
            userCache.putIfAbsent(user.getUsername(), user);
        }
        return user;
    }

    public User getUserCache(String username) {
        return userCache.get(username);
    }

    public void createUser(User user) {
        restTemplate.exchange("http://user-service/user/register", POST, null, User.class);
        if (user != null) {
            userCache.putIfAbsent(user.getUsername(), user);
        }
    }

    public boolean loginUser(String username, String password) {
        String body = restTemplate.exchange("http://user-service/user/login", GET, null, String.class).getBody();
        return body != null;
    }

    public boolean logoutUser() {
        return true;
    }

    @HystrixCommand(
            fallbackMethod = "getUsersCache",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
            }
    )
    public List<User> getUsers() {
        List<User> users = restTemplate.exchange("http://user-service/users", GET, null, new ParameterizedTypeReference<List<User>>() {
        }).getBody();
        if (users != null) {
            for (User user : users) {
                userCache.putIfAbsent(user.getUsername(), user);
            }
        }
        return users;
    }

    public List<User> getUsersCache(String username) {
        List<User> users = new ArrayList<>(new HashSet<>(userCache.values()));
        return users;
    }

    public boolean deleteById(String userId) {
        ResponseEntity<Void> response = restTemplate.exchange("http://user-service/user/{userId}", DELETE, null, Void.class, userId);
        return response.getStatusCode().equals(HttpStatus.OK);
    }
}
