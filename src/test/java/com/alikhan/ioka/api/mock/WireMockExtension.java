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
                            .withStatus(201)
                            .withHeader("Content-Type", "application/json")
                            .withBody("""
            {
              "order": {
                "id": "ord_123",
                "shop_id": "shop_001",
                "status": "EXPIRED",
                "created_at": "2025-01-01T12:00:00Z",
                "amount": 50000,
                "currency": "KZT",
                "capture_method": "AUTO",
                "external_id": "ext_123",
                "description": "Test order",
                "extra_info": {},
                "attempts": 1,
                "due_date": "2025-01-02T12:00:00Z",
                "customer_id": "cust_123",
                "card_id": "card_123",
                "back_url": "http://example.com/back",
                "success_url": "http://example.com/success",
                "failure_url": "http://example.com/failure",
                "template": "default",
                "checkout_url": "http://example.com/checkout",
                "access_token": "access_123",
                "mcc": "1234"
              },
              "order_access_token": "access_123"
            }
            """)));

            stubFor(get(urlEqualTo("/v2/orders/ord_123"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody("""
                                    {
                                       "id": "ord_123",
                                       "shop_id": "shop_001",
                                       "status": "EXPIRED",
                                       "created_at": "2025-01-01T12:00:00Z",
                                       "amount": 50000,
                                       "currency": "KZT",
                                       "capture_method": "AUTO",
                                       "external_id": "ext_123",
                                       "description": "Test order",
                                       "extra_info": {},
                                       "attempts": 1,
                                       "due_date": "2025-01-02T12:00:00Z",
                                       "customer_id": "cust_123",
                                       "card_id": "card_123",
                                       "back_url": "http://example.com",
                                       "success_url": "http://example.com",
                                       "failure_url": "http://example.com",
                                       "template": "default",
                                       "checkout_url": "http://example.com",
                                       "access_token": "string",
                                       "mcc": "string"
                                     }
                                    """)));

            stubFor(get(urlEqualTo("/v2/orders/not-found"))
                    .willReturn(aResponse()
                            .withStatus(404)
                            .withHeader("Content-Type", "application/json")
                            .withBody("""
                                    {
                                       "code": "404",
                                       "message": "ORDER_NOT_FOUND"
                                     }
                                    """)));

            stubFor(get(urlEqualTo("/v2/orders/ord_123/payments"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody("""
                                    [
                                        {
                                          "id": "string",
                                          "shop_id": "string",
                                          "order_id": "string",
                                          "status": "PENDING",
                                          "created_at": "string",
                                          "approved_amount": 0,
                                          "captured_amount": 0,
                                          "refunded_amount": 0,
                                          "processing_fee": 0,
                                          "payer": {
                                            "type": "CARD",
                                            "pan_masked": "string",
                                            "expiry_date": "string",
                                            "holder": "string",
                                            "payment_system": "string",
                                            "emitter": "string",
                                            "email": "user@example.com",
                                            "phone": "string",
                                            "customer_id": "string",
                                            "card_id": "string"
                                          },
                                          "error": {
                                            "code": "string",
                                            "message": "string"
                                          },
                                          "acquirer": {
                                            "name": "string",
                                            "reference": "string"
                                          }
                                        }
                                      ]
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
