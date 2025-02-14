package praktikum.client;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import praktikum.constants.EndPoints;

import static io.restassured.RestAssured.given;

public class BaseHttpClient {
    private static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri(EndPoints.BASE_HOST)
            .addHeader("Content-Type","application/json")
            .addFilter(new RequestLoggingFilter())
            .addFilter(new ResponseLoggingFilter())
            .build();
    @Step("Отправка POST запроса")
    public static Response postRequest(String endpoint, Object body) {
        return given()
                .spec(requestSpecification)
                .body(body)
                .when()
                .post(endpoint)
                .thenReturn();
    }

    @Step("Отправка DELETE запроса")
    public static void deleteRequest(String endpoint, String token) {
        given()
                .spec(requestSpecification)
                .header("Authorization", token)
                .delete(endpoint);
    }
}
