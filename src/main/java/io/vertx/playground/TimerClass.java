//
//public class TimerClass {

// public static void main(String[] args) {
//
//
//
// long timerID = vertx.setPeriodic(3000, new Handler<Long>() {
//
// public void handle(Long aLong) {
// System.out.println("Timer 1 fired: " + aLong);
// }
// });
//
// }
//}






package io.vertx.playground;





import io.vertx.core.Vertx;
//write an application receive the request
public class TimerClass {





private static int counter = 0;





public static void main(String... args) {
Vertx vertx = Vertx.vertx();





long timerId = vertx.setPeriodic(1000, id -> {
System.out.println("Hello " + ++counter);
});
}





}