package com.alikhan.ioka.api.client;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

import static com.alikhan.ioka.config.TestConfig.testConfig;
import static io.restassured.RestAssured.given;

public class IokaApiClient {

    static {
        RestAssured.baseURI = testConfig().apiBaseUrl();
    }

    public ValidatableResponse createOrder(int amount) {
        return given()
                .header("API-KEY", testConfig().apiKey())
                .contentType("application/json")
                .body("""
                        { "amount": %d }
                        """.formatted(amount))
                .when()
                .post("/v2/orders")
                .then();
    }

    public ValidatableResponse getOrder(String orderId) {
        return given()
                .header("API-KEY", testConfig().apiKey())
                .when()
                .get("/v2/orders/{id}", orderId)
                .then();
    }

    public ValidatableResponse getPayments(String orderId) {
        return given()
                .when()
                .get("/v2/orders/{id}/payments", orderId)
                .then();
    }}
