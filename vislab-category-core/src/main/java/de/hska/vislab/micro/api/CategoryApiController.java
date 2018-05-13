package de.hska.vislab.micro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hska.vislab.dbm.MongoConfigurationManager;
import de.hska.vislab.dbm.repositories.CategoryRepository;
import de.hska.vislab.model.Category;
import de.hska.vislab.model.ModelApiResponse;
import io.swagger.annotations.ApiParam;
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

@javax.annotation.Generated(
  value = "io.swagger.codegen.languages.SpringCodegen",
  date = "2018-05-09T06:42:04.687Z"
)
@Controller
public class CategoryApiController implements CategoryApi {

  private static final Logger log = LoggerFactory.getLogger(CategoryApiController.class);

  private final ObjectMapper objectMapper;

  private final HttpServletRequest request;

  @org.springframework.beans.factory.annotation.Autowired
  public CategoryApiController(ObjectMapper objectMapper, HttpServletRequest request) {
    this.objectMapper = objectMapper;
    this.request = request;
  }

  public ResponseEntity<ModelApiResponse> addCategory(
      @ApiParam(value = "Category object that needs to be added to the store", required = true)
          @Valid
          @RequestBody
          Category body) {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/json")) {
      CategoryRepository categoryRepository =
          MongoConfigurationManager.getInstance().getBean(CategoryRepository.class);
      if (!categoryRepository.existsByName(body.getName())) {
        Category newCategory = new Category(body.getName());
        categoryRepository.save(newCategory);
        ModelApiResponse modelApiResponse =
            new ModelApiResponse().code("200").type("OK").message("successful operation");
        return new ResponseEntity<ModelApiResponse>(modelApiResponse, HttpStatus.OK);
      }
    }
    return new ResponseEntity<ModelApiResponse>(HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<Void> deleteCategory(
      @Min(1)
          @ApiParam(value = "ID of the category that needs to be deleted", required = true)
          @PathVariable("categoryId")
          String categoryId) {
    String accept = request.getHeader("Accept");

    CategoryRepository categoryRepository =
        MongoConfigurationManager.getInstance().getBean(CategoryRepository.class);
    if (categoryRepository.existsById(categoryId)) {
      categoryRepository.deleteById(categoryId);
      return new ResponseEntity<Void>(HttpStatus.OK);
    }
    return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<List<Category>> getCategories() {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/json")) {

      CategoryRepository categoryRepository =
          MongoConfigurationManager.getInstance().getBean(CategoryRepository.class);
      List<Category> categoryList = categoryRepository.findAll();

      return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
    }

    return new ResponseEntity<List<Category>>(HttpStatus.BAD_REQUEST);
  }
}
