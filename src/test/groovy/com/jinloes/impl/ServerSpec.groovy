package com.jinloes.impl

import com.jinloes.Server
import com.jinloes.api.ElevatorControlSystem
import com.jinloes.model.PickUpCall
import com.jinloes.model.PickUpDirection
import io.vertx.core.Vertx
import io.vertx.core.http.HttpClientResponse
import io.vertx.core.json.JsonObject
import spock.lang.Shared
import spock.lang.Specification
import spock.util.concurrent.BlockingVariable

import java.util.concurrent.TimeUnit

import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat

/**
 * Tests for {@link Server}.
 */
class ServerSpec extends Specification {
    @Shared
    def Vertx vertx = Vertx.vertx()
    @Shared
    def int port
    def clientResponse
    def String deployId
    def ElevatorControlSystem elevatorControlSystem

    def setupSpec() {
        def socket = new ServerSocket(0)
        port = socket.getLocalPort()
        socket.close();
    }

    def setup() {
        def deployResult = new BlockingVariable<String>()
        clientResponse = new BlockingVariable<HttpClientResponse>()
        elevatorControlSystem = Mock(ElevatorControlSystem)
        vertx.deployVerticle(new Server(elevatorControlSystem, port), { result ->
            if(result.succeeded()) {
                deployResult.set(result.result())
            } else {
                throw new RuntimeException("Could not deploy server")
            }
        })
        deployId = deployResult.get()
    }

    def cleanup() {
        def result = new BlockingVariable<Void>()
        vertx.undeploy(deployId, { undeployResult ->
            result.set(undeployResult.result())
        })
        result.get()
    }

    def "A server should be able to handle a request to add a destination to an elevator"() {
        when: "send a request to add a destination"
            vertx.createHttpClient().post(port, "localhost", "/elevator/destination", { response ->
                clientResponse.set(response)
            }).end(new JsonObject().put("destination", 10).encode())
            clientResponse.get()
        then: "the destination request should be passed to the control system"
            1 * elevatorControlSystem.addDestination(10)
            _ * elevatorControlSystem.step()
            _ * elevatorControlSystem.toString()
            def int status = clientResponse.get().statusCode()
            assertThat status, equalTo(204)
    }

    def "A server should be able to handle a request for a pickup"() {
        when: "send a request to add a destination"
            vertx.createHttpClient().post(port, "localhost", "/pickup", { response ->
                clientResponse.set(response)
            }).end(new JsonObject().put("direction", "up").put("floor", 2).encode())
            clientResponse.get()
        then: "the destination request should be passed to the control system"
            1 * elevatorControlSystem.processPickUpCall(PickUpCall.of(PickUpDirection.UP, 2))
            _ * elevatorControlSystem.step()
            _ * elevatorControlSystem.toString()
            def int status = clientResponse.get().statusCode()
            assertThat status, equalTo(204)
    }
}