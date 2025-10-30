package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestUtils {
    private static final Properties properties = new Properties();
    private static final Logger logger = Logger.getLogger(TestUtils.class.getName());

    static {
        try (java.io.InputStream input = TestUtils.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                logger.warning("⚠️ Файл application.properties не найден, будут использованы значения по умолчанию.");
            } else {
                properties.load(input);
            }
        } catch (java.io.IOException ex) {
            logger.log(Level.SEVERE, "Ошибка при загрузке properties", ex);
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url", "https://stellarburgers.education-services.ru");
    }

    public static String getBrowserType() {
        String browserType = properties.getProperty("browser.type");
        logger.info("🔄 Определен браузер для тестов: " + browserType);
        return browserType.toLowerCase();
    }

    public static WebDriver createDriver() {
        String browserType = getBrowserType().toLowerCase();
        logger.info("🔄 Запуск тестов в браузере: " + browserType);

        WebDriver driver;

        if (browserType.equals("yandex")) {
            driver = setupYandexBrowser();
        } else {
            driver = setupChromeBrowser();
        }

        // Переход на сайт
        driver.get(getBaseUrl());
        logger.info("✅ Переход на сайт: " + getBaseUrl());

        return driver;
    }

    private static WebDriver setupChromeBrowser() {
        logger.info("✅ Запуск тестов в Google Chrome");

        // Получаем путь к chromedriver.exe из resources
        URL chromeDriverUrl = TestUtils.class.getClassLoader().getResource("chromedriver.exe");
        if (chromeDriverUrl == null) {
            throw new RuntimeException("Файл chromedriver.exe не найден в resources");
        }

        try {
            String chromeDriverPath = chromeDriverUrl.toURI().getPath();
            // Убираем начальный слеш для Windows
            if (chromeDriverPath.startsWith("/")) {
                chromeDriverPath = chromeDriverPath.substring(1);
            }
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            logger.info("✅ ChromeDriver загружен из resources: " + chromeDriverPath);
        } catch (java.net.URISyntaxException e) {
            throw new RuntimeException("Ошибка при получении пути к ChromeDriver", e);
        }

        // Настройка опций Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--start-maximized");

        return new ChromeDriver(options);
    }

    private static WebDriver setupYandexBrowser() {
        logger.info("✅ Запуск тестов в Яндекс.Браузере");

        // Получаем путь к yandexdriver.exe из resources
        URL yandexDriverUrl = TestUtils.class.getClassLoader().getResource("yandexdriver.exe");
        if (yandexDriverUrl == null) {
            throw new RuntimeException("Файл yandexdriver.exe не найден в resources");
        }

        try {
            String yandexDriverPath = yandexDriverUrl.toURI().getPath();
            // Убираем начальный слеш для Windows
            if (yandexDriverPath.startsWith("/")) {
                yandexDriverPath = yandexDriverPath.substring(1);
            }
            System.setProperty("webdriver.chrome.driver", yandexDriverPath);
            logger.info("✅ YandexDriver загружен из resources: " + yandexDriverPath);
        } catch (java.net.URISyntaxException e) {
            throw new RuntimeException("Ошибка при получении пути к YandexDriver", e);
        }

        // Специальные опции для Яндекс.Браузера
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--start-maximized");

        return new ChromeDriver(options);
    }
}