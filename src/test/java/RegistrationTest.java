import org.junit.*;
import org.openqa.selenium.WebDriver;
import page.LoginPage;
import page.RegisterPage;
import utils.DataGenerator;
import utils.TestUtils;
import utils.UserManager;
import model.User;

public class RegistrationTest {
    private WebDriver driver;
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private User testUser;

    @Before
    public void setUp() {
        driver = TestUtils.createDriver();
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
    }

    @Test
    public void successfulRegistrationTest() {
        try {
            // Генерация тестовых данных
            String name = DataGenerator.generateRandomName();
            String email = DataGenerator.generateRandomEmail();
            String password = DataGenerator.generateRandomPassword(10, 20);

            System.out.println("=== ТЕСТ УСПЕШНОЙ РЕГИСТРАЦИИ ===");
            System.out.println("Имя: " + name);
            System.out.println("Email: " + email);
            System.out.println("Пароль: " + maskPassword(password));

            // Переход и регистрация
            driver.get(TestUtils.getBaseUrl() + "/register");
            registerPage.waitForLoadRegisterPage();
            registerPage.registration(name, email, password);

            // Проверка успешной регистрации - должны перейти на страницу логина
            loginPage.waitForLoadEntrance();
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue("После регистрации должны быть на странице входа. Текущий URL: " + currentUrl,
                    currentUrl.contains("/login"));

            // Сохраняем пользователя для удаления в @After
            testUser = new User(email, password, name);

            System.out.println("✅ Регистрация успешна");

        } catch (Exception e) {
            System.out.println("❌ Ошибка в тесте регистрации: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    public void failedRegistrationTest() {
            // Генерация тестовых данных
            String name = DataGenerator.generateRandomName();
            String email = DataGenerator.generateRandomEmail();
            String password = DataGenerator.generateRandomPassword(1, 5);

            System.out.println("=== ТЕСТ НА ПОЯВЛЕНИЕ ОШИБКИ ПРИ НЕУСПЕШНОЙ РЕГИСТРАЦИИ ===");
            System.out.println("Имя: " + name);
            System.out.println("Email: " + email);
            System.out.println("Пароль: " + maskPassword(password));

            // Переход и регистрация
            driver.get(TestUtils.getBaseUrl() + "/register");
            registerPage.waitForLoadRegisterPage();
            registerPage.registration(name, email, password);
            Assert.assertTrue("Текст об ошибке на базе", driver.findElement(registerPage.errorPasswordText).isDisplayed());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        // Удаляем пользователя если он был создан в successfulRegistrationTest
        if (testUser != null && testUser.getEmail() != null) {
            try {
                // Логинимся чтобы получить accessToken для удаления
                UserManager.deleteUserByCredentials(testUser.getEmail(), testUser.getPassword());
            } catch (Exception e) {
                System.out.println("⚠️ Не удалось удалить тестового пользователя: " + e.getMessage());
            }
        }
    }

    private String maskPassword(String password) {
        if (password == null) return "null";
        return "*".repeat(password.length());
    }
}