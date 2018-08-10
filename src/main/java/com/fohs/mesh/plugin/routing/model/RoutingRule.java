package com.fohs.mesh.plugin.routing.model;

import io.vertx.core.http.HttpMethod;
import lombok.Getter;

@Getter
public class RoutingRule {

  private String method;

  private String serverPath;

  private String systemPath;

  public HttpMethod getHttpMethod() {
    return HttpMethod.valueOf(method);
  }
}
