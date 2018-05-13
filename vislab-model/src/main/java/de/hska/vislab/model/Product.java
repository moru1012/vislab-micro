package de.hska.vislab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

/** Product */
@Validated
@javax.annotation.Generated(
  value = "io.swagger.codegen.languages.SpringCodegen",
  date = "2018-05-09T06:42:04.687Z"
)
@Document
public class Product implements java.io.Serializable {
  @JsonProperty("id")
  @Id
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("price")
  private Double price = null;

  @JsonProperty("category")
  private String category = null;

  @JsonProperty("details")
  private String details = null;

  public Product id(String id) {
    this.id = id;
    return this;
  }

  public Product() {
    this("", 0.0, null);
  }

  public Product(String name, double price, String category) {
    this(new ObjectId().toString(), name, price, category, "");
  }

  public Product(String name, double price, String category, String details) {
    this(new ObjectId().toString(), name, price, category, details);
  }

  @PersistenceConstructor
  public Product(String id, String name, double price, String category, String details) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.category = category;
    this.details = details;
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

  public Product name(String name) {
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

  public Product price(Double price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   *
   * @return price
   */
  @ApiModelProperty(value = "")
  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Product category(String category) {
    this.category = category;
    return this;
  }

  /**
   * Get category
   *
   * @return category
   */
  @ApiModelProperty(value = "")
  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Product details(String details) {
    this.details = details;
    return this;
  }

  /**
   * Get details
   *
   * @return details
   */
  @ApiModelProperty(value = "")
  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(this.id, product.id)
        && Objects.equals(this.name, product.name)
        && Objects.equals(this.price, product.price)
        && Objects.equals(this.category, product.category)
        && Objects.equals(this.details, product.details);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, price, category, details);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Product {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    details: ").append(toIndentedString(details)).append("\n");
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
