package model;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static constants.TestData.*;
import static io.restassured.RestAssured.given;

public class UserClientSteps {

    @Step("Создание уникального пользователя")
    public static Response createUniqueUser(User user) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(REGISTER)
                .then()
                .extract().response();
    }

    @Step("Логин пользователя")
    public static Response loginUser(User user) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(LOGIN)
                .then()
                .extract().response();
    }

    @Step("Удаление пользователя")
    public static Response deleteUser(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .when()
                .delete(USER);
    }

    @Step("Получение данных о пользователе")
    public static Response getUserInfo(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .when()
                .get(USER);
    }
}