import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;


public class ConstructorTest extends BaseTest {

    @Test
    @DisplayName("Переход к разделу «Булки»")
    @Description("Проверка перехода к разделу булок в конструкторе")
    public void bunsSectionTest() {
        // Ждем загрузки страницы
        mainPage.waitForPageLoad();
        mainPage.clickOnBunsButton();
        mainPage.checkToppingBun();
    }

    @Test
    @DisplayName("Переход к разделу «Соусы»")
    @Description("Проверка перехода к разделу соусов в конструкторе")
    public void saucesSectionTest() {
        mainPage.waitForPageLoad();
        mainPage.clickOnSaucesButton();
        mainPage.checkToppingSauce();
    }

    @Test
    @DisplayName("Переход к разделу «Начинки»")
    @Description("Проверка перехода к разделу начинок в конструкторе")
    public void fillingsSectionTest() {
        mainPage.waitForPageLoad();
        mainPage.clickOnFillingButton();
        mainPage.checkToppingFillings();
    }
}