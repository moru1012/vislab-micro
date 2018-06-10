/**
 * NOTE: This class is auto generated by the swagger code generator program (2.3.1).
 * https://github.com/swagger-api/swagger-codegen Do not edit the class manually.
 */
package de.hska.vislab.micro.api;

import de.hska.vislab.model.Product;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@javax.annotation.Generated(
        value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-09T06:42:04.687Z"
)
@Api(value = "productsComp", description = "the products composite API")
public interface ProductsCompApi {

    @ApiOperation(
            value = "Delete products with category",
            nickname = "deleteProductwithCategory",
            notes = "",
            authorizations = {
                    @Authorization(
                            value = "store_auth",
                            scopes = {}
                    )
            },
            tags = {
                    "products",
                    "category"
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "Category not found")
            }
    )
    @RequestMapping(
            value = "/products/products/{categoryId}",
            produces = {"application/json"},
            method = RequestMethod.DELETE
    )
    ResponseEntity<Void> deleteProductWithCategory(
            @Min(1)
            @ApiParam(value = "ID of the category that needs to be deleted", required = true)
            @PathVariable("categoryId")
                    String categoryId);

    @ApiOperation(
            value = "Finds Products",
            nickname = "findProduct",
            notes = "Multiple query values can be provided with comma separated strings",
            response = Product.class,
            responseContainer = "List",
            authorizations = {
                    @Authorization(
                            value = "store_auth",
                            scopes = {}
                    )
            },
            tags = {
                    "products",
                    "category"
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "successful operation",
                            response = Product.class,
                            responseContainer = "List"
                    ),
                    @ApiResponse(code = 400, message = "Invalid product value")
            }
    )
    @RequestMapping(
            value = "/products/query",
            produces = {"application/json"},
            method = RequestMethod.GET
    )
    ResponseEntity<List<Product>> findProduct(
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
                    Double priceMax);
}
