package com.jinloes;

import com.jinloes.api.ElevatorControlSystem;
import com.jinloes.impl.ElevatorControlSystemVerticle;
import com.jinloes.model.PickUpCall;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.jacpfx.vertx.spring.SpringVerticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Vertx configuration class.
 */
@Component
@SpringVerticle(springConfig = AppConfig.class, autoremoveOtherSpringVerticles = false)
public class Server extends AbstractVerticle {
    private final ElevatorControlSystem elevatorControlSystem;
    private final int port;
    private HttpServer httpServer;

    @Autowired
    public Server(@Qualifier("elevatorControlSystemProxy") ElevatorControlSystem elevatorControlSystem,
                  @Value("${server.port:8080}") int port) {
        this.elevatorControlSystem = elevatorControlSystem;
        this.port = port;
    }

    @Override
    public void start() {
        vertx.setPeriodic(TimeUnit.SECONDS.toMillis(1), event -> elevatorControlSystem.step());
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.post("/elevator/destination").handler(routingContext -> {
            JsonObject body = routingContext.getBodyAsJson();
            elevatorControlSystem.addDestination(body.getInteger("destination"));
            HttpServerResponse response = routingContext.response();
            response.setStatusCode(204);
            response.end();
        });
        router.post("/pickup").handler(routingContext -> {
            JsonObject body = routingContext.getBodyAsJson();
            elevatorControlSystem.processPickUpCall(new PickUpCall(body));
            HttpServerResponse response = routingContext.response();
            response.setStatusCode(204);
            response.end();
        });
        httpServer = vertx.createHttpServer().requestHandler(router::accept).listen(port);
    }

    @Override
    public void stop() {
        httpServer.close();
    }
}
