package de.hska.vislab.micro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hska.vislab.dbm.MongoConfigurationManager;
import de.hska.vislab.dbm.repositories.ProductRepository;
import de.hska.vislab.model.ModelApiResponse;
import de.hska.vislab.model.Product;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@javax.annotation.Generated(
  value = "io.swagger.codegen.languages.SpringCodegen",
  date = "2018-05-09T06:42:04.687Z"
)
@Controller
public class ProductsApiController implements ProductsApi {

  private static final Logger log = LoggerFactory.getLogger(ProductsApiController.class);

  private final ObjectMapper objectMapper;

  private final HttpServletRequest request;

  @org.springframework.beans.factory.annotation.Autowired
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

      ProductRepository productRepository =
          MongoConfigurationManager.getInstance().getBean(ProductRepository.class);

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

    ProductRepository productRepository =
        MongoConfigurationManager.getInstance().getBean(ProductRepository.class);
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
      ProductRepository productRepository =
          MongoConfigurationManager.getInstance().getBean(ProductRepository.class);
      List<Product> productList = null;

      if (search != null) {
        if (priceMin != null) {
          if (priceMax != null) {
            productList =
                productRepository.findByNameAndPriceGreaterThanEqualAndPriceIsLessThanEqual(
                    search, priceMin, priceMax);
          } else {
            productList = productRepository.findByNameAndPriceGreaterThanEqual(search, priceMin);
          }
        } else {
          if (priceMax != null) {
            productList = productRepository.findByNameAndPriceIsLessThanEqual(search, priceMax);
          } else {
            productList = productRepository.findByName(search);
          }
        }
      } else {
        if (priceMin != null) {
          if (priceMax != null) {
            productList =
                productRepository.findByPriceGreaterThanEqualAndPriceIsLessThanEqual(
                    priceMin, priceMax);
          } else {
            productList = productRepository.findByPriceGreaterThanEqual(priceMin);
          }
        } else {
          if (priceMax != null) {
            productList = productRepository.findByPriceIsLessThanEqual(priceMax);
          } else {
            productList = productRepository.findAll();
          }
        }
      }
      if (productList != null) {
        return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
      }
    }

    return new ResponseEntity<List<Product>>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<Product> findProductById(
      @Min(1)
          @ApiParam(value = "ID of the product that needs to be found", required = true)
          @PathVariable("productId")
          String productId) {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/json")) {

      ProductRepository productRepository =
          MongoConfigurationManager.getInstance().getBean(ProductRepository.class);

      if (productRepository.existsById(productId)) {
        Product product = productRepository.findById(productId).get();
        return new ResponseEntity<Product>(product, HttpStatus.OK);
      }
    }

    return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<List<Product>> getProducts() {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/json")) {

      ProductRepository productRepository =
          MongoConfigurationManager.getInstance().getBean(ProductRepository.class);

      List<Product> productList = productRepository.findAll();

      return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
    }
    return new ResponseEntity<List<Product>>(HttpStatus.BAD_REQUEST);
  }
}
