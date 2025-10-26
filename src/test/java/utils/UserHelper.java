package utils;

import io.restassured.response.Response;

import static constants.TestData.*;
import static io.restassured.RestAssured.given;

public class UserHelper {

    public static String createTestUser(String email, String password, String name) {
        String requestBody = String.format(
                "{\"email\": \"%s\", \"password\": \"%s\", \"name\": \"%s\"}",
                email, password, name
        );

        Response response = given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post(REGISTER);

        if (response.statusCode() == 200) {
            return response.jsonPath().getString("accessToken");
        }
        return null;
    }

    public static void deleteTestUser(String accessToken) {
        if (accessToken != null && !accessToken.isEmpty()) {
            given()
                    .header("Authorization", accessToken)
                    .when()
                    .delete(USER)
                    .then()
                    .statusCode(202);
        }
    }

    // Новый метод для получения ингредиентов
    public static String getIngredientId() {
        Response response = given()
                .when()
                .get(INGREDIENTS);

        if (response.statusCode() == 200) {
            return response.jsonPath().getString("data[0]._id");
        }
        return null;
    }
}