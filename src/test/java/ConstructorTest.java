import org.junit.Test;
import org.openqa.selenium.By;

public class ConstructorTest extends BaseTest {

    @Test
    public void bunsSectionTest() {
        // Ждем загрузки страницы
        mainPage.waitForPageLoad();
        mainPage.clickOnBunsButton();
        mainPage.checkToppingBun();
    }

    @Test
    public void saucesSectionTest() {
        mainPage.waitForPageLoad();
        mainPage.clickOnSaucesButton();
        mainPage.checkToppingSauce();
    }

    @Test
    public void fillingsSectionTest() {
        mainPage.waitForPageLoad();
        mainPage.clickOnFillingButton();
        mainPage.checkToppingFillings();
    }
}