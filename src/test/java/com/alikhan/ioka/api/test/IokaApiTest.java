package com.alikhan.ioka.api.test;

import com.alikhan.ioka.api.client.IokaApiClient;
import com.alikhan.ioka.api.mock.WireMockExtension;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.alikhan.ioka.config.TestConfig.testConfig;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ExtendWith(WireMockExtension.class)
public class IokaApiTest {

    private final IokaApiClient api = new IokaApiClient();
    private final Long timeout = testConfig().apiTimeoutMillis();

    @Test
    void createOrderShouldReturn201AndValidBody() {
        api.createOrder(50000)
                .statusCode(201)
                .time(lessThan(timeout))
                .body("order.id", equalTo("ord_123"))
                .body("order.amount", equalTo(50000))
                .body("order.currency", equalTo("KZT"))
                .body("order.status", is(oneOf("ON_HOLD", "UNPAID", "PAID", "EXPIRED")))
                .body("order.shop_id", not(is(emptyOrNullString())))
                .body("order.back_url", startsWith("http"))
                .body("order.success_url", startsWith("http"))
                .body("order.failure_url", startsWith("http"))
                .body("order.access_token", not(is(emptyOrNullString())))
                .body("order_access_token", not(is(emptyOrNullString())));
    }

    @Test
    @Story("Получение заказа")
    @Description("Успешное получение существующего заказа по id")
    void getOrderShouldReturn200() {
        api.getOrder("ord_123")
                .statusCode(200)
                .time(lessThan(timeout))
                .body("id", equalTo("ord_123"))
                .body("amount", greaterThan(0))
                .body("currency", equalTo("KZT"))
                .body("status", equalTo("EXPIRED"));
    }

    @Test
    @Story("Ошибка 404")
    @Description("Невалидный id заказа возвращает 404 и тело с ошибкой")
    void getOrderShouldReturn404ForInvalidId() {
        api.getOrder("not-found")
                .statusCode(404)
                .time(lessThan(timeout))
                .body("code", equalTo("404"))
                .body("message", equalTo("ORDER_NOT_FOUND"));
    }

    @Test
    void getPaymentsShouldReturn200AndValidList() {

        api.getPayments("ord_123")
                .statusCode(200)
                .time(lessThan(timeout))
                .body("$", hasSize(greaterThanOrEqualTo(1)))
                .body("[0].id", not(is(emptyOrNullString())))
                .body("[0].shop_id", not(is(emptyOrNullString())))
                .body("[0].order_id", not(is(emptyOrNullString())))
                .body("[0].status", equalTo("PENDING"))
                .body("[0].approved_amount", equalTo(0))
                .body("[0].captured_amount", equalTo(0))
                .body("[0].refunded_amount", equalTo(0))
                .body("[0].processing_fee", equalTo(0))
                .body("[0].payer.type", equalTo("CARD"))
                .body("[0].payer.email", equalTo("user@example.com"))
                .body("[0].payer.card_id", not(is(emptyOrNullString())))
                .body("[0].error.code", not(is(emptyOrNullString())))
                .body("[0].error.message", not(is(emptyOrNullString())))
                .body("[0].acquirer.name", not(is(emptyOrNullString())))
                .body("[0].acquirer.reference", not(is(emptyOrNullString())));
    }

}
