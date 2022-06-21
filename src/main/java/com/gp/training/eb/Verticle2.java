package com.gp.training.eb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class Verticle2 extends AbstractVerticle{
	  @Override
	  public void start(Promise<Void> promise) throws Exception {
        vertx.eventBus().consumer("vert1", this::onMessage);
	  }
	  
	  public void onMessage(Message<JsonObject> msg) {
		    JsonObject msgBody = msg.body();
		    System.out.println("In incomeing request ="+msgBody);
		    msg.reply(new JsonObject().put("US Price", "1000 $"));
		  }
	  
	  public static void main(String ar[]) {
		   VertxOptions options = new VertxOptions();
		   Vertx.clusteredVertx(options, cluster -> {
		       if (cluster.succeeded()) {
		           cluster.result().deployVerticle(new Verticle2(), res -> {
		               if(res.succeeded()){
		                  System.out.println("-------- deployed");
		               } else {
		            	   System.out.println("---error ");
		               }
		           });
		       } else {
		    	   
		    	   System.out.println("failed");
		       }
	  });
	  }
}