package com.alikhan.ioka.api.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.extension.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockExtension implements BeforeAllCallback, AfterAllCallback {

    private static WireMockServer server;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {

        if (server == null) {
            server = new WireMockServer(8089);
            server.start();
            configureFor("localhost", 8089);

            stubFor(post(urlEqualTo("/v2/orders"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody("""
                                    {
                                      "id": "ord_123",
                                      "amount": 50000,
                                      "currency": "KZT",
                                      "status": "PENDING"
                                    }
                                    """)));

            stubFor(get(urlEqualTo("/v2/orders/ord_123"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody("""
                                    {
                                      "id": "ord_123",
                                      "amount": 50000,
                                      "currency": "KZT",
                                      "status": "PENDING"
                                    }
                                    """)));

            stubFor(get(urlEqualTo("/v2/orders/not-found"))
                    .willReturn(aResponse()
                            .withStatus(404)
                            .withHeader("Content-Type", "application/json")
                            .withBody("""
                                    {
                                      "error": "ORDER_NOT_FOUND",
                                      "message": "Order not found"
                                    }
                                    """)));

        }

    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {

        if (server != null) {
            server.stop();
            server = null;
        }

    }

}
