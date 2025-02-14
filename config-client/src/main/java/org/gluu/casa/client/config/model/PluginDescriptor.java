/*
 * Gluu casa admin API
 * Allows to configure Casa programmatically. Note that plugins may also expose endpoints to apply configurations relevant to their topics. Check plugins' docs for more information
 *
 * OpenAPI spec version: 5.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package org.gluu.casa.client.config.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PluginDescriptor
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-07-18T14:15:05.565Z")
public class PluginDescriptor {
  @JsonProperty("pluginId")
  private String pluginId = null;

  @JsonProperty("version")
  private String version = null;

  @JsonProperty("pluginDescription")
  private String pluginDescription = null;

  @JsonProperty("pluginClass")
  private String pluginClass = null;

  public PluginDescriptor pluginId(String pluginId) {
    this.pluginId = pluginId;
    return this;
  }

   /**
   * Get pluginId
   * @return pluginId
  **/
  @ApiModelProperty(required = true, value = "")
  public String getPluginId() {
    return pluginId;
  }

  public void setPluginId(String pluginId) {
    this.pluginId = pluginId;
  }

  public PluginDescriptor version(String version) {
    this.version = version;
    return this;
  }

   /**
   * Get version
   * @return version
  **/
  @ApiModelProperty(value = "")
  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public PluginDescriptor pluginDescription(String pluginDescription) {
    this.pluginDescription = pluginDescription;
    return this;
  }

   /**
   * Get pluginDescription
   * @return pluginDescription
  **/
  @ApiModelProperty(value = "")
  public String getPluginDescription() {
    return pluginDescription;
  }

  public void setPluginDescription(String pluginDescription) {
    this.pluginDescription = pluginDescription;
  }

  public PluginDescriptor pluginClass(String pluginClass) {
    this.pluginClass = pluginClass;
    return this;
  }

   /**
   * The fully qualified name of the Java class that implements the plugin
   * @return pluginClass
  **/
  @ApiModelProperty(value = "The fully qualified name of the Java class that implements the plugin")
  public String getPluginClass() {
    return pluginClass;
  }

  public void setPluginClass(String pluginClass) {
    this.pluginClass = pluginClass;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PluginDescriptor pluginDescriptor = (PluginDescriptor) o;
    return Objects.equals(this.pluginId, pluginDescriptor.pluginId) &&
        Objects.equals(this.version, pluginDescriptor.version) &&
        Objects.equals(this.pluginDescription, pluginDescriptor.pluginDescription) &&
        Objects.equals(this.pluginClass, pluginDescriptor.pluginClass);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pluginId, version, pluginDescription, pluginClass);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PluginDescriptor {\n");
    
    sb.append("    pluginId: ").append(toIndentedString(pluginId)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    pluginDescription: ").append(toIndentedString(pluginDescription)).append("\n");
    sb.append("    pluginClass: ").append(toIndentedString(pluginClass)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

