package de.hska.vislab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

/** Role */
@Validated
@javax.annotation.Generated(
  value = "io.swagger.codegen.languages.SpringCodegen",
  date = "2018-05-09T06:42:04.687Z"
)
@Document
public class Role {
  @JsonProperty("id")
  @Id
  private String id = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("level")
  private Integer level = null;

  public Role id(String id) {
    this.id = id;
    return this;
  }

  public Role() {
    this(new ObjectId().toString(), "", 0);
  }

  public Role(String type, int level) {
    this(new ObjectId().toString(), type, level);
  }

  @PersistenceConstructor
  public Role(String id, String type, int level) {
    this.id = id;
    this.type = type;
    this.level = level;
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

  public Role type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   *
   * @return type
   */
  @ApiModelProperty(value = "")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Role level(Integer level) {
    this.level = level;
    return this;
  }

  /**
   * Get level
   *
   * @return level
   */
  @ApiModelProperty(value = "")
  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Role role = (Role) o;
    return Objects.equals(this.id, role.id)
        && Objects.equals(this.type, role.type)
        && Objects.equals(this.level, role.level);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, level);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Role {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    level: ").append(toIndentedString(level)).append("\n");
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
