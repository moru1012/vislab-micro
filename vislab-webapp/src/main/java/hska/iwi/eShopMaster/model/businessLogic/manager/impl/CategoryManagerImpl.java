package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import de.hska.vislab.model.Category;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import java.util.Arrays;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

public class CategoryManagerImpl implements CategoryManager {
    private RestTemplate restTemplate;

    public CategoryManagerImpl(String username, String password) {
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

    public List<Category> getCategories() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ((OAuth2RestTemplate) restTemplate).getAccessToken().getValue());
        return restTemplate.exchange("http://localhost:9090/category-service/category", GET, new HttpEntity<>(null, headers), new ParameterizedTypeReference<List<Category>>() {}).getBody();
    }

    public Category getCategory(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ((OAuth2RestTemplate) restTemplate).getAccessToken().getValue());
        return restTemplate.exchange("http://localhost:9090/category-service/category/{categoryId}", GET, new HttpEntity<>(null, headers), Category.class, id).getBody();
    }

    public Category getCategoryByName(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ((OAuth2RestTemplate) restTemplate).getAccessToken().getValue());
        return restTemplate.exchange("http://localhost:9090/category-service/category/name/{name}", GET, new HttpEntity<>(null, headers), Category.class, name).getBody();
    }

    public void addCategory(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ((OAuth2RestTemplate) restTemplate).getAccessToken().getValue());
        HttpEntity<Category> request = new HttpEntity<>(new Category(name), headers);
        restTemplate.exchange("http://localhost:9090/category-service/category/addCategory", POST, request, Category.class);
    }

    public void delCategory(Category cat) {
        // 	Products are also deleted because of relation
        delCategoryById(cat.getId());
    }

    public void delCategoryById(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ((OAuth2RestTemplate) restTemplate).getAccessToken().getValue());
        restTemplate.exchange("http://localhost:9090/product-comp-service/products/category/{categoryId}", DELETE, new HttpEntity<>(null, headers), Void.class, id);
    }
}
