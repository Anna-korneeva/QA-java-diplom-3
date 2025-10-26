import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import page.LoginPage;
import utils.DataGenerator;
import utils.TestUtils;
import utils.UserManager;
import model.User;

public class LoginFromDifferentPlacesTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private User testUser;

    @Before
    public void setUp() {
        // Каждый тест создает своего пользователя через API
        testUser = UserManager.createTestUser();
        System.out.println("📧 Создан тестовый пользователь: " + testUser.getEmail());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        // Удаляем тестового пользователя после каждого теста
        UserManager.deleteTestUser(testUser);
        System.out.println("🧹 Тестовый пользователь удален");
    }

    @Test
    @DisplayName("1. Вход по кнопке «Войти в аккаунт» на главной")
    public void loginFromMainPageButtonTest() {
        driver = TestUtils.createDriver();
        loginPage = new LoginPage(driver);

        // Переход на главную страницу
        driver.get(TestUtils.getBaseUrl() + "/");

        // Нажимаем кнопку "Войти в аккаунт"
        driver.findElement(loginPage.getEnterAccountButton()).click();

        // Логин через Page Object
        loginPage.login(testUser.getEmail(), testUser.getPassword());

        // Полная проверка авторизации
        boolean isFullyAuthorized = loginPage.fullAuthorizationCheck(testUser.getEmail());

        Assert.assertTrue("Пользователь должен быть авторизован с правильным email",
                isFullyAuthorized);
    }

    @Test
    @DisplayName("2. Вход через кнопку «Личный кабинет»")
    public void loginFromPersonalAccountButtonTest() {
        driver = TestUtils.createDriver();
        loginPage = new LoginPage(driver);

        // Переход на главную страницу
        driver.get(TestUtils.getBaseUrl() + "/");

        // Нажимаем кнопку "Личный Кабинет"
        driver.findElement(loginPage.getPersonalAccountLink()).click();

        // Логин через Page Object
        loginPage.login(testUser.getEmail(), testUser.getPassword());

        // Полная проверка авторизации
        boolean isFullyAuthorized = loginPage.fullAuthorizationCheck(testUser.getEmail());

        Assert.assertTrue("Пользователь должен быть авторизован с правильным email",
                isFullyAuthorized);
    }

    @Test
    @DisplayName("3. Вход через кнопку в форме регистрации")
    public void loginFromRegistrationFormButtonTest() {
        driver = TestUtils.createDriver();
        loginPage = new LoginPage(driver);

        // Переход на страницу регистрации
        driver.get(TestUtils.getBaseUrl() + "/register");

        // Нажимаем на ссылку "Войти"
        loginPage.clickOnRegisterLink();

        // Логин через Page Object
        loginPage.login(testUser.getEmail(), testUser.getPassword());

        // Полная проверка авторизации
        boolean isFullyAuthorized = loginPage.fullAuthorizationCheck(testUser.getEmail());

        Assert.assertTrue("Пользователь должен быть авторизован с правильным email",
                isFullyAuthorized);
    }

    @Test
    @DisplayName("4. Вход через кнопку в форме восстановления пароля")
    public void loginFromPasswordRecoveryButtonTest() {
        driver = TestUtils.createDriver();
        loginPage = new LoginPage(driver);

        // Переход на страницу восстановления пароля
        driver.get(TestUtils.getBaseUrl() + "/forgot-password");

        // Нажимаем на ссылку "Войти"
        loginPage.clickOnForgotPasswordLink();

        // Логин через Page Object
        loginPage.login(testUser.getEmail(), testUser.getPassword());

        // Полная проверка авторизации
        boolean isFullyAuthorized = loginPage.fullAuthorizationCheck(testUser.getEmail());

        Assert.assertTrue("Пользователь должен быть авторизован с правильным email",
                isFullyAuthorized);
    }
}