package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import de.hska.vislab.model.Category;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

public class CategoryManagerImpl implements CategoryManager {
    private RestTemplate restTemplate;

    public CategoryManagerImpl() {
        restTemplate = new RestTemplate();
    }

    public List<Category> getCategories() {
//        return restTemplate.exchange("http://localhost:9090/category-service/category", GET, null, new ParameterizedTypeReference<List<Category>>() {
//        }).getBody();
        return restTemplate.exchange("http://localhost:8082/category", GET, null, new ParameterizedTypeReference<List<Category>>() {
        }).getBody();
    }

    public Category getCategory(String id) {
//        return restTemplate.exchange("http://localhost:9090/category-service/category/{categoryId}", GET, null, Category.class, id).getBody();
        return restTemplate.exchange("http://localhost:8082/category/{categoryId}", GET, null, Category.class, id).getBody();
    }

    public Category getCategoryByName(String name) {
//        return restTemplate.exchange("http://localhost:9090/category-service/category/name/{name}", GET, null, Category.class, name).getBody();
        return restTemplate.exchange("http://localhost:8082/category/name/{name}", GET, null, Category.class, name).getBody();
    }

    public void addCategory(String name) {
        HttpEntity<Category> request = new HttpEntity<>(new Category(name));
//        restTemplate.exchange("http://localhost:9090/category-service/category/addCategory", POST, request, Category.class);
        restTemplate.exchange("http://localhost:8082/category/addCategory", POST, request, Category.class);
    }

    public void delCategory(Category cat) {
        // 	Products are also deleted because of relation
        delCategoryById(cat.getId());
    }

    public void delCategoryById(String id) {
//        restTemplate.exchange("http://localhost:9090/product-comp-service/products/category/{categoryId}", DELETE, null, Void.class, id);
        restTemplate.exchange("http://localhost:8089/products/category/{categoryId}", DELETE, null, Void.class, id);
    }
}
