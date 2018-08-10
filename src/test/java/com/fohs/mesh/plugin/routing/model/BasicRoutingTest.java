package com.fohs.mesh.plugin.routing.model;

import com.fohs.mesh.plugin.routing.MeshRouterPlugin;
import com.gentics.mesh.Mesh;
import com.gentics.mesh.plugin.Plugin;
import com.gentics.mesh.test.local.MeshLocalServer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;

public class BasicRoutingTest {

  private static Plugin plugin = new MeshRouterPlugin();

  @ClassRule
  public static final MeshLocalServer server = new MeshLocalServer()
          .withInMemoryMode()
          .waitForStartup();

  @BeforeClass
  public static void setupPlugin() {
    Mesh mesh = server.getMesh();
    mesh.getVertx().deployVerticle(plugin);
  }

  @Test
  public void firstRouteTest() throws IOException {
    Response testResponse = get("/test");
    int responseCode = testResponse.code();
    String responseBody = testResponse.body().string();

    Assert.assertEquals(200, responseCode);
    Assert.assertEquals("routed okay!", responseBody);
  }

  @Test
  public void secondRouteTest() throws IOException {
    Response testResponse = get("/app1");
    int responseCode = testResponse.code();
    String responseBody = testResponse.body().string();

    Assert.assertEquals(200, responseCode);
    Assert.assertEquals("SPA mounted!", responseBody);
  }

  private Response get(String path) throws IOException {
    Request request = new Request.Builder()
            .url("http://" + server.getHostname() + ":" + server.getPort() + path)
            .build();

    return client().newCall(request).execute();
  }

  private OkHttpClient client() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    return builder.build();
  }
}
