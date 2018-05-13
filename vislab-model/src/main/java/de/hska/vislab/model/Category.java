package de.hska.vislab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

/** Category */
@Validated
@javax.annotation.Generated(
  value = "io.swagger.codegen.languages.SpringCodegen",
  date = "2018-05-09T06:42:04.687Z"
)
@Document
public class Category {
  @JsonProperty("id")
  @Id
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("products")
  @Valid
  private List<String> products = null;

  public Category id(String id) {
    this.id = id;
    return this;
  }

  public Category() {
    this("");
  }

  public Category(String name) {
    this(new ObjectId().toString(), name, new ArrayList<>());
  }

  @PersistenceConstructor
  public Category(String id, String name, List<String> products) {
    this.id = id;
    this.name = name;
    this.products = new ArrayList<>();
  }

  /**
   * Get id
   *
   * @return id
   */
  @ApiModelProperty(value = "")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Category name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name
   */
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Category products(List<String> products) {
    this.products = products;
    return this;
  }

  public Category addProductsItem(String productsItem) {
    if (this.products == null) {
      this.products = new ArrayList<String>();
    }
    this.products.add(productsItem);
    return this;
  }

  /**
   * Get products
   *
   * @return products
   */
  @ApiModelProperty(value = "")
  public List<String> getProducts() {
    return products;
  }

  public void setProducts(List<String> products) {
    this.products = products;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Category category = (Category) o;
    return Objects.equals(this.id, category.id)
        && Objects.equals(this.name, category.name)
        && Objects.equals(this.products, category.products);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, products);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Category {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    products: ").append(toIndentedString(products)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
