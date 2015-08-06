package com.jinloes;

import com.jinloes.impl.ElevatorControlSystemImpl;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by rr2re on 8/5/2015.
 */
public class Server extends AbstractVerticle {
    private ElevatorControlSystemImpl elevatorControlSystem;

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        ElevatorControlSystemImpl elevatorControlSystem = new ElevatorControlSystemImpl();
        vertx.deployVerticle(new Server(elevatorControlSystem));
        vertx.deployVerticle(elevatorControlSystem);
    }

    public Server(ElevatorControlSystemImpl elevatorControlSystem) {
        this.elevatorControlSystem = elevatorControlSystem;
    }

    @Override
    public void start() {
        EventBus eventBus = vertx.eventBus();
        vertx.setPeriodic(TimeUnit.SECONDS.toMillis(1), event -> elevatorControlSystem.step());
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.post("/elevator/destination").handler(routingContext -> {
            JsonObject body = routingContext.getBodyAsJson();
            eventBus.send(ElevatorControlSystemImpl.ADD_DESTINATION_ADDRESS, body.getInteger("destination"));
            HttpServerResponse response = routingContext.response();
            response.setStatusCode(204);
        });
        router.post("/pickup").handler(routingContext -> {
            JsonObject body = routingContext.getBodyAsJson();
            eventBus.send(ElevatorControlSystemImpl.PICKUP_ADDRESS, body);
            HttpServerResponse response = routingContext.response();
            response.setStatusCode(204);
        });
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}
