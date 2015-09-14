package com.jinloes;

import com.jinloes.api.ElevatorControlSystem;
import com.jinloes.impl.ElevatorControlSystemImpl;
import com.jinloes.impl.ElevatorControlSystemVerticle;
import io.vertx.core.Vertx;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jinloes on 9/14/15.
 */
@Configuration
@ComponentScan
public class AppConfig {
    private static final String SPRING_VERTICLE_PREFIX = "java-spring:";
    private static Vertx VERTX;

    public static void main(String[] args) {
        VERTX = Vertx.vertx();
        VERTX.deployVerticle(SPRING_VERTICLE_PREFIX + Server.class.getCanonicalName());
        VERTX.deployVerticle(SPRING_VERTICLE_PREFIX + ElevatorControlSystemVerticle.class.getCanonicalName());
    }

    @Bean
    public ElevatorControlSystem elevatorControlSystem() {
        return new ElevatorControlSystemImpl();
    }

    @Bean
    public ElevatorControlSystem elevatorControlSystemProxy() {
        return ElevatorControlSystem.createProxy(VERTX,
                ElevatorControlSystemVerticle.ELEVATOR_SYSTEM_ADDRESS);
    }
}
