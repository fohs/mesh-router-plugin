package com.fohs.mesh.plugin.routing.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class RoutingConfig {

  @JsonProperty
  private List<RoutingRule> routes;
}
