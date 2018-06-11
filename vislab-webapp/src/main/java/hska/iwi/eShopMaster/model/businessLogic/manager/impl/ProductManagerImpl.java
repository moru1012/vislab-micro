package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import de.hska.vislab.model.Category;
import de.hska.vislab.model.Product;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import java.util.Arrays;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
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

    public ProductManagerImpl() {
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

    public List<Product> getProducts() {
        return restTemplate.exchange("http://gateway-service:9090/product-service/products", GET, null, new ParameterizedTypeReference<List<Product>>() {}).getBody();
    }

    public List<Product> getProductsForSearchValues(String searchDescription,
                                                    Double searchMinPrice, Double searchMaxPrice) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://gateway-service:9090/product-comp-service/products/query")
                .queryParam("search", searchDescription)
                .queryParam("price_min", searchMinPrice)
                .queryParam("price_max", searchMaxPrice);

        List<Product> productList = restTemplate.exchange(builder.toUriString(), GET, null,
            new ParameterizedTypeReference<List<Product>>() {
            }).getBody();
        return productList;
    }

    public Product getProductById(String id) {
        return restTemplate.exchange("http://gateway-service:9090/product-service/products/{productId}", GET, null, Product.class, id).getBody();
    }

    public Product getProductByName(String name) {
        return restTemplate.exchange("http://gateway-service:9090/product-service/products/name/{productName}", GET, null, Product.class, name).getBody();
    }

    public String addProduct(String name, double price, String categoryId, String details) {
        CategoryManager categoryManager = new CategoryManagerImpl();
        Category category = categoryManager.getCategory(categoryId);

        if (category != null) {
            Product product;
            if (details == null) {
                product = new Product(name, price, categoryId);
            } else {
                product = new Product(name, price, categoryId, details);
            }

            HttpEntity<Product> request = new HttpEntity<>(product);
            restTemplate.exchange("http://gateway-service:9090/product-service/products/addProduct", POST, request, Product.class);
            return product.getId();
        }
        return null;
    }


    public boolean deleteProductById(String id) {
        ResponseEntity<Void> response = restTemplate.exchange("http://gateway-service:9090/product-service/products/{productId}", DELETE, null, Void.class, id);
        return response.getStatusCode().equals(HttpStatus.OK);
    }

    public void deleteProductsByCategoryId(String categoryId) {
        restTemplate.exchange("http://gateway-service:9090/product-comp-service/products/category/{categoryId}", DELETE, null, Void.class, categoryId);
    }

}
