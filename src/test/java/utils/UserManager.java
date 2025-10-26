package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.User;

import static constants.TestData.*;

public class UserManager {

    static {
        // Устанавливаем базовый URL для всех REST Assured запросов
        RestAssured.baseURI = BASE_URI;
    }

    public static User createTestUser() {
        String email = DataGenerator.generateRandomEmail();
        String password = DataGenerator.generateRandomPassword(10, 20);
        String name = DataGenerator.generateRandomName();

        User user = new User(email, password, name);

        try {
            Response response = RestAssured.given()
                    .header("Content-type", "application/json")
                    .body(user)
                    .when()
                    .post(REGISTER);

            if (response.statusCode() == 200) {
                user.setAccessToken(response.jsonPath().getString("accessToken"));
                System.out.println("✅ Пользователь создан через API: " + email);
                return user;
            } else {
                System.out.println("⚠️ Не удалось создать пользователя. Status: " + response.statusCode() +
                        ", Body: " + response.getBody().asString());
                // Все равно возвращаем пользователя для UI тестов
                return user;
            }
        } catch (Exception e) {
            System.out.println("❌ Ошибка при создании пользователя через API: " + e.getMessage());
            // Все равно возвращаем пользователя для UI тестов
            return user;
        }
    }

    public static void deleteTestUser(User user) {
        if (user != null && user.getAccessToken() != null) {
            try {
                RestAssured.given()
                        .header("Authorization", user.getAccessToken())
                        .when()
                        .delete(USER)
                        .then()
                        .statusCode(202);
                System.out.println("✅ Пользователь удален через API");
            } catch (Exception e) {
                System.out.println("⚠️ Не удалось удалить пользователя через API: " + e.getMessage());
            }
        }
    }

    public static void deleteUserByCredentials(String email, String password) {
        try {
            // Сначала логинимся чтобы получить accessToken
            User loginUser = new User(email, password);
            Response loginResponse = RestAssured.given()
                    .header("Content-type", "application/json")
                    .body(loginUser)
                    .when()
                    .post(LOGIN);

            if (loginResponse.statusCode() == 200) {
                String accessToken = loginResponse.jsonPath().getString("accessToken");
                if (accessToken != null) {
                    // Удаляем пользователя с полученным токеном
                    RestAssured.given()
                            .header("Authorization", accessToken)
                            .when()
                            .delete(USER)
                            .then()
                            .statusCode(202);
                    System.out.println("✅ Пользователь удален по credentials: " + email);
                }
            } else {
                System.out.println("⚠️ Не удалось залогиниться для удаления пользователя. Status: " + loginResponse.statusCode());
            }
        } catch (Exception e) {
            System.out.println("⚠️ Не удалось удалить пользователя по credentials: " + e.getMessage());
        }
    }
}