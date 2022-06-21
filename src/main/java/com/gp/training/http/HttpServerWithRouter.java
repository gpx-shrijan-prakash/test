package com.gp.training.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;



public class HttpServerWithRouter extends AbstractVerticle {



public int port = 9091;
public HttpServer server;



@Override
public void start(Promise<Void> startPromise) throws Exception {
server = vertx.createHttpServer();
Router mainRouter = Router.router(vertx);

Router serv1Router = Router.router(vertx);
serv1Router
.get("/service1")
.consumes("application/json")
.produces("application/json")
.handler(this::processService1);

Router serv2Router = Router.router(vertx);
serv2Router
.get("/service2")
.consumes("application/json")
.produces("application/json")
.handler(this::processService2);

mainRouter.mountSubRouter("/services",serv1Router);
mainRouter.mountSubRouter("/services",serv2Router);

server.requestHandler(mainRouter).listen(port).onSuccess(s -> {
System.out.println("HTTP Server started on port ::" + port);
startPromise.complete();
}).onFailure(e -> {
e.printStackTrace();
startPromise.fail(e);
});
}

@Override
public void stop() throws Exception {

super.stop();
server.close();
}



public static void main(String ar[]) {
Vertx.vertx().deployVerticle(
new HttpServerWithRouter(),deployHandler->{
if(deployHandler.succeeded()){
System.out.println
(" Verticle Deployed 2");
} else {
System.out.println
(deployHandler.cause().getMessage());
}
});
}


public void processService1(RoutingContext ctx) {
try {
JsonObject resp= new JsonObject().put("msg", "Resp from serv1");
ctx.response().end(resp.encodePrettily());
}catch (Exception e) {
e.printStackTrace();
JsonObject error = new JsonObject().put("error", e.getLocalizedMessage());
ctx.response().end(error.encodePrettily());
}
}

public void processService2(RoutingContext ctx) {
try {
JsonObject resp= new JsonObject().put("msg", "Resp from serv2");
ctx.response().end(resp.encodePrettily());
}catch (Exception e) {
e.printStackTrace();
JsonObject error = new JsonObject().put("error", e.getLocalizedMessage());
ctx.response().end(error.encodePrettily());
}
}



}
