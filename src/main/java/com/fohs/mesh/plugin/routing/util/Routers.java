package com.fohs.mesh.plugin.routing.util;

import com.gentics.mesh.router.RootRouter;
import com.gentics.mesh.router.RouterStorage;
import io.vertx.ext.web.Router;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public enum Routers {
  ALL;

  @Getter
  private final List<Router> roots;

  Routers() {
    roots = RouterStorage.getInstances().stream()
            .map(RouterStorage::root)
            .map(RootRouter::getRouter)
            .collect(Collectors.toList());
  }
}
