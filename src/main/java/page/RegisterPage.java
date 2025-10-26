package page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {

    private final WebDriver driver;

    // Локаторы
    private final By nameField = By.xpath(".//input[@name='name']");
    private final By emailField = By.xpath(".//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    public final By errorPasswordText = By.xpath(".//p[text()='Некорректный пароль']");
    public final By registerText = By.xpath(".//h2[text()='Регистрация']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    // Ввод имени с явным ожиданием
    public void setName(String name) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(nameField));
        element.clear();
        element.sendKeys(name);
    }

    // Ввод Email с явным ожиданием
    public void setEmail(String email) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(emailField));
        element.clear();
        element.sendKeys(email);
    }

    // Ввод пароля с явным ожиданием
    public void setPassword(String password) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(passwordField));
        element.clear();
        element.sendKeys(password);
    }

    // Клик по кнопке "Зарегистрироваться"
    public void clickOnRegisterButton() {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(registerButton));
        element.click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Регистрация пользователя")
    public void registration(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        clickOnRegisterButton();
    }

    @Step("Выставлено ожидание загрузки страницы регистрации через текст 'Регистрация'.")
    public void waitForLoadRegisterPage() {
        new WebDriverWait(driver, Duration.ofSeconds(40))
                .until(ExpectedConditions.visibilityOfElementLocated(registerText));
    }

    @Step("Выставлено ожидание загрузки страницы полностью, анимация исчезнет.")
    public void waitForInvisibilityLoadingAnimation() {
        new WebDriverWait(driver, Duration.ofSeconds(40))
                .until(ExpectedConditions.invisibilityOfElementLocated
                        (By.xpath(".//img[@src='./static/media/loading.89540200.svg' and @alt='loading animation']")));
    }

    @Step("Проверка отображения ошибки")
    public boolean isErrorMessageDisplayed() {
        try {
            // Локатор для общей ошибки
            By errorLocator = By.xpath(".//p[contains(@class, 'input__error')]");
            return new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.visibilityOfElementLocated(errorLocator))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Получение текста ошибки")
    public String getErrorMessageText() {
        try {
            By errorLocator = By.xpath(".//p[contains(@class, 'input__error')]");
            return driver.findElement(errorLocator).getText();
        } catch (Exception e) {
            return "Ошибка не найдена";
        }
    }

    // Проверка что форма готова к заполнению
    public boolean isFormReady() {
        try {
            return driver.findElement(nameField).isDisplayed() &&
                    driver.findElement(emailField).isDisplayed() &&
                    driver.findElement(passwordField).isDisplayed() &&
                    driver.findElement(registerButton).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
}