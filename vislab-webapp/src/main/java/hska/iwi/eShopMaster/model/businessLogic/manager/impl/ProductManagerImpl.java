package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import de.hska.vislab.model.Category;
import de.hska.vislab.model.Product;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import java.util.Arrays;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.HttpMethod.*;


public class ProductManagerImpl implements ProductManager {
    private RestTemplate restTemplate;
    private ResourceOwnerPasswordResourceDetails resourceDetails;

    public ProductManagerImpl(String username, String password) {
        String tokenUri = "http://localhost:8081/auth/oauth/token";

        resourceDetails = new ResourceOwnerPasswordResourceDetails();
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

    public List<Product> getProducts() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ((OAuth2RestTemplate) restTemplate).getAccessToken().getValue());
        return restTemplate.exchange("http://localhost:9090/product-service/products", GET, new HttpEntity<>(null, headers), new ParameterizedTypeReference<List<Product>>() {}).getBody();
    }

    public List<Product> getProductsForSearchValues(String searchDescription,
                                                    Double searchMinPrice, Double searchMaxPrice) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:9090/product-comp-service/products/query")
                .queryParam("search", searchDescription)
                .queryParam("price_min", searchMinPrice)
                .queryParam("price_max", searchMaxPrice);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ((OAuth2RestTemplate) restTemplate).getAccessToken().getValue());
        List<Product> productList = restTemplate.exchange(builder.toUriString(), GET, new HttpEntity<>(null, headers),
            new ParameterizedTypeReference<List<Product>>() {
            }).getBody();
        return productList;
    }

    public Product getProductById(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ((OAuth2RestTemplate) restTemplate).getAccessToken().getValue());
        return restTemplate.exchange("http://localhost:9090/product-service/products/{productId}", GET, new HttpEntity<>(null, headers), Product.class, id).getBody();
    }

    public Product getProductByName(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ((OAuth2RestTemplate) restTemplate).getAccessToken().getValue());
        return restTemplate.exchange("http://localhost:9090/product-service/products/name/{productName}", GET, new HttpEntity<>(null, headers), Product.class, name).getBody();
    }

    public String addProduct(String name, double price, String categoryId, String details) {
        CategoryManager categoryManager = new CategoryManagerImpl(resourceDetails.getUsername(), resourceDetails.getPassword());
        Category category = categoryManager.getCategory(categoryId);

        if (category != null) {
            Product product;
            if (details == null) {
                product = new Product(name, price, categoryId);
            } else {
                product = new Product(name, price, categoryId, details);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + ((OAuth2RestTemplate) restTemplate).getAccessToken().getValue());
            HttpEntity<Product> request = new HttpEntity<>(product, headers);
            restTemplate.exchange("http://localhost:9090/product-service/products/addProduct", POST, request, Product.class);
            return product.getId();
        }
        return null;
    }


    public boolean deleteProductById(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ((OAuth2RestTemplate) restTemplate).getAccessToken().getValue());
//        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:9090/product-service/products/{productId}", DELETE, null, Void.class, id);
        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:8083/products/{productId}", DELETE, new HttpEntity<>(null, headers), Void.class, id);
        return response.getStatusCode().equals(HttpStatus.OK);
    }

    public void deleteProductsByCategoryId(String categoryId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ((OAuth2RestTemplate) restTemplate).getAccessToken().getValue());
//        restTemplate.exchange("http://localhost:9090/product-comp-service/products/category/{categoryId}", DELETE, null, Void.class, categoryId);
        restTemplate.exchange("http://localhost:8088/products/category/{categoryId}", DELETE, new HttpEntity<>(null, headers), Void.class, categoryId);
    }

}
