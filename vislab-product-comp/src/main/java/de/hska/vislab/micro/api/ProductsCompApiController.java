package de.hska.vislab.micro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hska.vislab.model.Category;
import de.hska.vislab.model.Product;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.*;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;

@javax.annotation.Generated(
        value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-09T06:42:04.687Z"
)
@RestController
public class ProductsCompApiController implements ProductsCompApi {

    private static final Logger log = LoggerFactory.getLogger(ProductsCompApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public ProductsCompApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> deleteProductWithCategory(
            @Min(1)
            @ApiParam(value = "ID of the product that needs to be deleted", required = true)
            @PathVariable("categoryId")
                    String categoryId) {
        String accept = request.getHeader("Accept");
        restTemplate.exchange("http://category-service/category/{categoryId}", DELETE, null, Void.class, categoryId);
        List<Product> productList = restTemplate.exchange("http://product-service/products/category/{categoryId}", GET, null, new ParameterizedTypeReference<List<Product>>() {
        }, categoryId).getBody();
        if (productList != null) {
            if (!Objects.requireNonNull(productList).isEmpty()) {
                for (Product product : productList) {
                    restTemplate.exchange("http://product-service/products/{productId}", DELETE, null, Void.class, product.getId());
                }
            }
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<List<Product>> findProduct(
            @ApiParam(value = "name value that need to be considered for filter")
            @Valid
            @RequestParam(value = "search", required = false)
                    String search,
            @ApiParam(value = "min price value that need to be considered for filter")
            @Valid
            @RequestParam(value = "price_min", required = false)
                    Double priceMin,
            @ApiParam(value = "max price value that need to be considered for filter")
            @Valid
            @RequestParam(value = "price_max", required = false)
                    Double priceMax) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://category-service/category/query")
                    .queryParam("search", search);

            List<Category> categoryList =
                    restTemplate.exchange(builder.toUriString(), GET, null, new ParameterizedTypeReference<List<Category>>() {
                    }).getBody();

            builder = UriComponentsBuilder.fromHttpUrl("http://product-service/products/query")
                    .queryParam("search", search)
                    .queryParam("price_min", priceMin)
                    .queryParam("price_max", priceMax);

            List<Product> productList =
                    restTemplate.exchange(builder.toUriString(), GET, null, new ParameterizedTypeReference<List<Product>>() {
                    }).getBody();

            if (categoryList != null) {
                Set<Product> productSet = new HashSet<>(productList);

                for (Category category : Objects.requireNonNull(categoryList)) {
                    builder = UriComponentsBuilder.fromHttpUrl("http://product-service/products/query")
                            .queryParam("search", category.getId())
                            .queryParam("price_min", priceMin)
                            .queryParam("price_max", priceMax);
                    ResponseEntity<List<Product>> responseSearchCategory =
                            restTemplate.exchange(builder.toUriString(), GET, null, new ParameterizedTypeReference<List<Product>>() {
                            });
                    if (responseSearchCategory != null) {
                        productSet.addAll(Objects.requireNonNull(responseSearchCategory.getBody()));
                    }
                }
                productList = new ArrayList<>(productSet);
            }
            if (productList != null) {
                return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
            }
        }

        return new ResponseEntity<List<Product>>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }
}
