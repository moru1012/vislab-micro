package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import de.hska.vislab.model.Category;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import java.util.Arrays;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
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

    public CategoryManagerImpl() {
        OAuth2ClientContext clientContext = new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest());

        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setUsername("roy");
        resourceDetails.setPassword("spring");
        resourceDetails.setAccessTokenUri(" http://localhost:8081/auth/oauth/token");
        resourceDetails.setClientId("clientapp");
        resourceDetails.setClientSecret("123456");
        resourceDetails.setGrantType("password");
        resourceDetails.setScope(Arrays.asList("read", "write"));
        restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
    }

    public List<Category> getCategories() {
        return restTemplate.exchange("http://gateway-service:9090/category-service/category", GET, null, new ParameterizedTypeReference<List<Category>>() {
        }).getBody();
    }

    public Category getCategory(String id) {
        return restTemplate.exchange("http://gateway-service:9090/category-service/category/{categoryId}", GET, null, Category.class, id).getBody();
    }

    public Category getCategoryByName(String name) {
        return restTemplate.exchange("http://gateway-service:9090/category-service/category/name/{name}", GET, null, Category.class, name).getBody();
    }

    public void addCategory(String name) {
        HttpEntity<Category> request = new HttpEntity<>(new Category(name));
        restTemplate.exchange("http://gateway-service:9090/category-service/category/addCategory", POST, request, Category.class);
    }

    public void delCategory(Category cat) {
        // 	Products are also deleted because of relation
        delCategoryById(cat.getId());
    }

    public void delCategoryById(String id) {
        restTemplate.exchange("http://gateway-service:9090/product-comp-service/products/category/{categoryId}", DELETE, null, Void.class, id);
    }
}
