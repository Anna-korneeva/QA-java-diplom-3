package page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final String baseUrl = "https://stellarburgers.education-services.ru";

    // Локаторы для страницы логина
    private final By emailField = By.xpath(".//input[@type='text']");
    private final By passwordField = By.xpath(".//input[@type='password']");
    private final By loginButton = By.xpath(".//button[text()='Войти']");
    private final By registerLink = By.xpath(".//a[text()='Зарегистрироваться']");
    private final By forgotPasswordLink = By.xpath(".//a[text()='Восстановить пароль']");
    private final By entranceText = By.xpath("//h2[contains(text(), 'Вход')]");

    // Локаторы для проверки авторизации на главной странице
    private final By makeOrderButton = By.xpath(".//button[text()='Оформить заказ']");
    private final By personalAccountLink = By.xpath(".//p[text()='Личный Кабинет']");
    private final By enterAccountButton = By.xpath("//button[text()='Войти в аккаунт']");

    // Локаторы для личного кабинета
    private final By profileEmailField = By.xpath(".//input[@name='name' and @type='text']");
    private final By profileSection = By.xpath(".//a[text()='Профиль']");

    private final By forgotPasswordText = By.xpath(".//h2[text()='Восстановление пароля']");
    private final By errorMessage = By.xpath(".//p[contains(@class, 'input__error')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Получить локатор кнопки 'Войти в аккаунт'")
    public By getEnterAccountButton() {
        return enterAccountButton;
    }

    @Step("Получить локатор кнопки 'Личный Кабинет'")
    public By getPersonalAccountLink() {
        return personalAccountLink;
    }

    @Step("Ожидание загрузки главной страницы")
    public void waitForMainPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.urlToBe(baseUrl + "/"));
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(makeOrderButton));
    }

    @Step("Клик по кнопке 'Войти в аккаунт'")
    public void clickOnEnterAccountButton() {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(enterAccountButton));
        element.click();
    }

    @Step("Клик по кнопке 'Личный Кабинет'")
    public void clickOnPersonalAccountLink() {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(personalAccountLink));
        element.click();
    }

    @Step("Переход на главную страницу")
    public void goToMainPage() {
        driver.get(baseUrl + "/");
        waitForMainPageLoad();
    }

    @Step("Ожидание загрузки страницы входа")
    public void waitForLoadEntrance() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(entranceText));
    }

    @Step("Клик по ссылке 'Зарегистрироваться'")
    public void clickOnRegisterLink() {
        // На странице регистрации используем локатор для ссылки "Войти"
        By loginLink = By.xpath(".//a[text()='Войти']");
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(loginLink));
        element.click();
    }



    @Step("Клик по ссылке 'Восстановить пароль'")
    public void clickOnForgotPasswordLink() {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(".//a[text()='Войти']")));
        element.click();
    }

    @Step("Ввод email")
    public void setEmail(String email) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(emailField));
        element.clear();
        element.sendKeys(email);
    }

    @Step("Ввод пароля")
    public void setPassword(String password) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(passwordField));
        element.clear();
        element.sendKeys(password);
    }

    @Step("Клик по кнопке 'Войти'")
    public void clickOnLoginButton() {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(loginButton));
        element.click();
    }

    @Step("Логин пользователя")
    public void login(String email, String password) {
        try {
            System.out.println("=== ПРОЦЕСС ЛОГИНА ===");
            System.out.println("URL до логина: " + driver.getCurrentUrl());

            setEmail(email);
            setPassword(password);
            clickOnLoginButton();

            // Ждем завершения логина - либо переходим на главную, либо показывается ошибка
            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.or(
                            ExpectedConditions.urlToBe(baseUrl + "/"),
                            ExpectedConditions.visibilityOfElementLocated(errorMessage)
                    ));

            System.out.println("URL после логина: " + driver.getCurrentUrl());

        } catch (Exception e) {
            System.out.println("Ошибка в процессе логина: " + e.getMessage());
            throw e;
        }
    }

    @Step("Авторизация пользователя")
    public void authorization(String email, String password) {
        login(email, password);
    }

    @Step("Проверка успешной авторизации по кнопке 'Оформить заказ'")
    public boolean isAuthorized() {
        try {
            // Проверяем наличие кнопки "Оформить заказ" на главной странице
            boolean hasMakeOrderButton = isElementPresent(makeOrderButton);

            System.out.println("=== ДИАГНОСТИКА АВТОРИЗАЦИИ ===");
            System.out.println("Кнопка 'Оформить заказ' присутствует: " + hasMakeOrderButton);
            System.out.println("Текущий URL: " + driver.getCurrentUrl());

            boolean isAuthorized = hasMakeOrderButton && driver.getCurrentUrl().equals(baseUrl + "/");

            System.out.println("РЕЗУЛЬТАТ: " + (isAuthorized ? "✅ АВТОРИЗОВАН" : "❌ НЕ АВТОРИЗОВАН"));

            return isAuthorized;

        } catch (Exception e) {
            System.out.println("Ошибка в методе isAuthorized: " + e.getMessage());
            return false;
        }
    }

    @Step("Проверка email в личном кабинете")
    public boolean verifyEmailInPersonalAccount(String expectedEmail) {
        try {
            System.out.println("=== ПРОВЕРКА EMAIL В ЛИЧНОМ КАБИНЕТЕ ===");
            System.out.println("Ожидаемый email: " + expectedEmail);

            // 1. Кликаем на "Личный Кабинет"
            WebElement accountLink = new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.elementToBeClickable(personalAccountLink));
            accountLink.click();

            // 2. Ждем загрузки личного кабинета
            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.visibilityOfElementLocated(profileSection));

            // 3. Получаем значение email из поля
            WebElement emailField = driver.findElement(profileEmailField);
            String actualEmail = emailField.getAttribute("value");

            System.out.println("Фактический email в профиле: " + actualEmail);

            // 4. Сравниваем email ИГНОРИРУЯ РЕГИСТР
            boolean emailsMatch = expectedEmail.equalsIgnoreCase(actualEmail);

            System.out.println("Email совпадают (игнорируя регистр): " + emailsMatch);

            return emailsMatch;

        } catch (Exception e) {
            System.out.println("Ошибка при проверке email в личном кабинете: " + e.getMessage());
            return false;
        }
    }

    @Step("Полная проверка авторизации с верификацией email")
    public boolean fullAuthorizationCheck(String expectedEmail) {
        try {
            System.out.println("=== ПОЛНАЯ ПРОВЕРКА АВТОРИЗАЦИИ ===");

            // 1. Проверяем что мы на главной странице и авторизованы
            boolean isOnMainPage = driver.getCurrentUrl().equals(baseUrl + "/");

            if (!isOnMainPage) {
                System.out.println("❌ Не на главной странице после логина. Текущий URL: " + driver.getCurrentUrl());
                return false;
            }

            boolean isAuthorized = isAuthorized();

            if (!isAuthorized) {
                System.out.println("❌ Не авторизован (нет кнопки 'Оформить заказ')");
                return false;
            }

            // 2. Проверяем email в личном кабинете
            boolean emailVerified = verifyEmailInPersonalAccount(expectedEmail);

            if (!emailVerified) {
                System.out.println("❌ Email в личном кабинете не совпадает");
                return false;
            }

            System.out.println("✅ ПОЛНАЯ ПРОВЕРКА ПРОЙДЕНА: пользователь авторизован и email верный");
            return true;

        } catch (Exception e) {
            System.out.println("Ошибка в полной проверке авторизации: " + e.getMessage());
            return false;
        }
    }

    @Step("Проверка отображения ошибки авторизации")
    public boolean isErrorMessageDisplayed() {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.visibilityOfElementLocated(errorMessage))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Получение текста ошибки")
    public String getErrorMessageText() {
        try {
            return driver.findElement(errorMessage).getText();
        } catch (Exception e) {
            return "Ошибка не найдена";
        }
    }

    @Step("Проверка перехода на страницу восстановления пароля")
    public boolean isForgotPasswordPageLoaded() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.visibilityOfElementLocated(forgotPasswordText));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверка перехода на страницу регистрации")
    public boolean isRegisterPageLoaded() {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.urlContains("/register"));
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Переход на страницу логина")
    public void goToLoginPage() {
        driver.get(baseUrl + "/login");
        waitForLoadEntrance();
    }

    // Вспомогательный метод для проверки наличия элемента
    private boolean isElementPresent(By locator) {
        try {
            return driver.findElements(locator).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Ожидание невидимости анимации загрузки")
    public void waitForInvisibilityLoadingAnimation() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.invisibilityOfElementLocated
                            (By.xpath(".//img[@src='./static/media/loading.89540200.svg' and @alt='loading animation']")));
        } catch (Exception e) {
            // Игнорируем исключение, если элемент не найден
        }
    }
}