import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import page.LoginPage;
import page.MainPage;


import page.RegisterPage;
import utils.TestUtils;

import java.time.Duration;


public class BaseTest {
    protected WebDriver driver;
    protected String baseUrl;

    MainPage mainPage;
    RegisterPage registerPage;
    LoginPage loginPage;


    @Before
    public void setUp() {
        driver = TestUtils.createDriver();
        driver.manage().window().maximize();
        baseUrl = TestUtils.getBaseUrl();

        driver.get(baseUrl);

        mainPage = new MainPage(driver);
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        driver.navigate().refresh();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}