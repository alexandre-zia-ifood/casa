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
import java.util.ArrayList;
import java.util.List;

/**
 * OxdSettings
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-07-18T14:15:05.565Z")
public class OxdSettings {
  @JsonProperty("protocol")
  private String protocol = null;

  @JsonProperty("host")
  private String host = null;

  @JsonProperty("port")
  private String port = null;

  @JsonProperty("scopes")
  private List<String> scopes = new ArrayList<>();

  @JsonProperty("frontchannel_logout_uri")
  private String frontchannelLogoutUri = null;

  @JsonProperty("post_logout_uri")
  private String postLogoutUri = null;

  @JsonProperty("authz_redirect_uri")
  private String authzRedirectUri = null;

  public OxdSettings protocol(String protocol) {
    this.protocol = protocol;
    return this;
  }

   /**
   * Get protocol
   * @return protocol
  **/
  @ApiModelProperty(required = true, value = "")
  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public OxdSettings host(String host) {
    this.host = host;
    return this;
  }

   /**
   * Get host
   * @return host
  **/
  @ApiModelProperty(required = true, value = "")
  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public OxdSettings port(String port) {
    this.port = port;
    return this;
  }

   /**
   * Get port
   * @return port
  **/
  @ApiModelProperty(required = true, value = "")
  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public OxdSettings scopes(List<String> scopes) {
    this.scopes = scopes;
    return this;
  }

  public OxdSettings addScopesItem(String scopesItem) {
    this.scopes.add(scopesItem);
    return this;
  }

   /**
   * Get scopes
   * @return scopes
  **/
  @ApiModelProperty(required = true, value = "")
  public List<String> getScopes() {
    return scopes;
  }

  public void setScopes(List<String> scopes) {
    this.scopes = scopes;
  }

  public OxdSettings frontchannelLogoutUri(String frontchannelLogoutUri) {
    this.frontchannelLogoutUri = frontchannelLogoutUri;
    return this;
  }

   /**
   * Get frontchannelLogoutUri
   * @return frontchannelLogoutUri
  **/
  @ApiModelProperty(required = true, value = "")
  public String getFrontchannelLogoutUri() {
    return frontchannelLogoutUri;
  }

  public void setFrontchannelLogoutUri(String frontchannelLogoutUri) {
    this.frontchannelLogoutUri = frontchannelLogoutUri;
  }

  public OxdSettings postLogoutUri(String postLogoutUri) {
    this.postLogoutUri = postLogoutUri;
    return this;
  }

   /**
   * Get postLogoutUri
   * @return postLogoutUri
  **/
  @ApiModelProperty(required = true, value = "")
  public String getPostLogoutUri() {
    return postLogoutUri;
  }

  public void setPostLogoutUri(String postLogoutUri) {
    this.postLogoutUri = postLogoutUri;
  }

  public OxdSettings authzRedirectUri(String authzRedirectUri) {
    this.authzRedirectUri = authzRedirectUri;
    return this;
  }

   /**
   * Get authzRedirectUri
   * @return authzRedirectUri
  **/
  @ApiModelProperty(required = true, value = "")
  public String getAuthzRedirectUri() {
    return authzRedirectUri;
  }

  public void setAuthzRedirectUri(String authzRedirectUri) {
    this.authzRedirectUri = authzRedirectUri;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OxdSettings oxdSettings = (OxdSettings) o;
    return Objects.equals(this.protocol, oxdSettings.protocol) &&
        Objects.equals(this.host, oxdSettings.host) &&
        Objects.equals(this.port, oxdSettings.port) &&
        Objects.equals(this.scopes, oxdSettings.scopes) &&
        Objects.equals(this.frontchannelLogoutUri, oxdSettings.frontchannelLogoutUri) &&
        Objects.equals(this.postLogoutUri, oxdSettings.postLogoutUri) &&
        Objects.equals(this.authzRedirectUri, oxdSettings.authzRedirectUri);
  }

  @Override
  public int hashCode() {
    return Objects.hash(protocol, host, port, scopes, frontchannelLogoutUri, postLogoutUri, authzRedirectUri);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OxdSettings {\n");
    
    sb.append("    protocol: ").append(toIndentedString(protocol)).append("\n");
    sb.append("    host: ").append(toIndentedString(host)).append("\n");
    sb.append("    port: ").append(toIndentedString(port)).append("\n");
    sb.append("    scopes: ").append(toIndentedString(scopes)).append("\n");
    sb.append("    frontchannelLogoutUri: ").append(toIndentedString(frontchannelLogoutUri)).append("\n");
    sb.append("    postLogoutUri: ").append(toIndentedString(postLogoutUri)).append("\n");
    sb.append("    authzRedirectUri: ").append(toIndentedString(authzRedirectUri)).append("\n");
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

