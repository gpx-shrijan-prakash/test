
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class VertxMaven  extends AbstractVerticle {
	public static int port = 9090;
	public HttpServer server;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        
        router.route().handler(routingContext -> {

        	  // This handler will be called for every request
        	  HttpServerResponse response = routingContext.response();
        	  response.putHeader("content-type", "text/plain");

        	  // Write to the response and end it
        	  response.end("Hello World from Vert.x-Web!");
        });
		server.requestHandler(router).listen(port).onSuccess(s -> {
			System.out.println("HTTP Server started on port ::" + port);
			startPromise.complete();
		}).onFailure(e -> {
			e.printStackTrace();
			startPromise.fail(e);
		});
	}
    
    public static void main(String ar[]) {
    	Vertx.vertx().deployVerticle(
                new VertxMaven(),deployHandler->{
            if(deployHandler.succeeded()){
                System.out.println
                        (" Verticle Deployed");
            } else {
                System.out.println
                        (deployHandler.cause().getMessage());
            }
        });
    }
}
