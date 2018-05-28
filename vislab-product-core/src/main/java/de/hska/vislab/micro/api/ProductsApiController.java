package de.hska.vislab.micro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hska.vislab.dbm.repositories.ProductRepository;
import de.hska.vislab.model.ModelApiResponse;
import de.hska.vislab.model.Product;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(
        value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-09T06:42:04.687Z"
)
@RestController
public class ProductsApiController implements ProductsApi {

    private static final Logger log = LoggerFactory.getLogger(ProductsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public ProductsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<ModelApiResponse> addProduct(
            @ApiParam(value = "Product object that needs to be added to the store", required = true)
            @Valid
            @RequestBody
                    Product body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Product newProduct =
                    new Product(body.getName(), body.getPrice(), body.getCategory(), body.getDetails());
            productRepository.save(newProduct);

            ModelApiResponse modelApiResponse =
                    new ModelApiResponse().code("200").type("OK").message("successful operation");
            return new ResponseEntity<ModelApiResponse>(modelApiResponse, HttpStatus.OK);
        }
        return new ResponseEntity<ModelApiResponse>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    public ResponseEntity<Void> deleteProduct(
            @Min(1)
            @ApiParam(value = "ID of the product that needs to be deleted", required = true)
            @PathVariable("productId")
                    String productId) {
        String accept = request.getHeader("Accept");
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
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
            List<Product> productList = null;

            double priceMinParam = Double.MIN_VALUE;
            double priceMaxParam = Double.MAX_VALUE;
            if (search != null) {
                if (priceMin != null) {
                    priceMinParam = priceMin-1;
                }
                if (priceMax != null) {
                    priceMaxParam = priceMax+1;
                }
                productList = productRepository.findByNameOrCategoryOrDetailsRegexAndPriceBetween(search, search, search, priceMinParam, priceMaxParam);
            } else {
                if (priceMin != null) {
                    priceMinParam = priceMin-1;
                }
                if (priceMax != null) {
                    priceMaxParam = priceMax+1;
                }
                productList = productRepository.findByPriceGreaterThanEqualAndPriceBetween(priceMinParam, priceMaxParam);
            }
            if (productList != null) {
                return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
            }
        }

        return new ResponseEntity<List<Product>>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Product> getProductById(
            @Min(1)
            @ApiParam(value = "ID of the product that needs to be found", required = true)
            @PathVariable("productId")
                    String productId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            if (productRepository.existsById(productId)) {
                Product product = productRepository.findById(productId).get();
                return new ResponseEntity<Product>(product, HttpStatus.OK);
            }
        }

        return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Product> getProductByName(
            @Min(1)
            @ApiParam(value = "Name of the product that needs to be found", required = true)
            @PathVariable("productName")
                    String productName) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Product productList = productRepository.findByName(productName);
            return new ResponseEntity<Product>(productList, HttpStatus.OK);
        }
        return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Product>> getProducts() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Product> productList = productRepository.findAll();

            return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
        }
        return new ResponseEntity<List<Product>>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Product>> getByCategory(
            @Min(1)
            @ApiParam(value = "ID of the category for the products", required = true)
            @PathVariable("categoryId")
                    String categoryId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Product> productList = productRepository.findByCategory(categoryId);
            return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
        }
        return new ResponseEntity<List<Product>>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }
}
