package com.fohs.mesh.plugin.routing;

import com.fohs.mesh.plugin.routing.model.RoutingConfig;
import com.fohs.mesh.plugin.routing.util.Routers;
import com.gentics.mesh.plugin.AbstractPluginVerticle;
import io.reactivex.Completable;
import io.vertx.core.Handler;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.reactivex.core.buffer.Buffer;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MeshRouterPlugin extends AbstractPluginVerticle {

  @Override
  public Completable initialize() {

    log.info("Setting up custom routes");

    return getRxVertx()
            .fileSystem()
            .rxReadFile("config/routing.json")
            .map(Buffer::toJsonObject)
            .map(json -> json.mapTo(RoutingConfig.class))
            .map(this::setupRoutes)
            .toCompletable();
  }

  private List<Route> setupRoutes(RoutingConfig routingConfig) {
    return Routers.ALL.getRoots().stream()
            .flatMap(rootRouter -> mount(routingConfig, rootRouter))
            .collect(Collectors.toList());
  }

  private Stream<Route> mount(RoutingConfig routingConfig, Router router) {
    return routingConfig.getRoutes().stream()
            .peek(route -> log.info(String.format("Route: %s -> %s", route.getServerPath(), route.getSystemPath())))
            .map(route -> router
                    .route(route.getHttpMethod(), route.getServerPath())
                    .handler(staticHandler(route.getSystemPath()))
            );
  }

  private Handler<RoutingContext> staticHandler(String root) {
    return StaticHandler.create(root);
  }

  @Override
  public void registerEndpoints(Router globalRouter, Router projectRouter) {
  }
}
