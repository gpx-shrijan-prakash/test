import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;



public class Service
{
public Future<JsonObject> getResponse(Vertx vertx, JsonObject req)
{
Promise<JsonObject> responseFut = Promise.promise();
vertx
.eventBus()
.request(
"vert1", req,resp->{
if(resp.succeeded())
{
responseFut.complete((JsonObject) resp.result().body());
}
else
{
responseFut.complete(new JsonObject().put("error", "unexpected error"));
}
}
);
return responseFut.future();
}
}