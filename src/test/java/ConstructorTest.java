import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ConstructorTest extends BaseTest {

    @Test
    @DisplayName("Переход к разделу «Булки»")
    @Description("Проверка перехода к разделу булок в конструкторе")
    public void bunsSectionTest() {
        // Ждем загрузки страницы
        mainPage.waitForPageLoad();
        mainPage.clickOnBunsButton();
        mainPage.checkToppingBun();
        assertTrue("Раздел булок должен быть активен", mainPage.isBunsSectionActive());
        assertTrue("Заголовок раздела булок должен отображаться", mainPage.isBunsSectionTitleDisplayed());
        assertTrue("Должны присутствовать элементы булок", mainPage.getBunsCount() > 0);
    }

    @Test
    @DisplayName("Переход к разделу «Соусы»")
    @Description("Проверка перехода к разделу соусов в конструкторе")
    public void saucesSectionTest() {
        mainPage.waitForPageLoad();
        mainPage.clickOnSaucesButton();
        mainPage.checkToppingSauce();
        assertTrue("Раздел соусов должен быть активен", mainPage.isSaucesSectionActive());
        assertTrue("Заголовок раздела соусов должен отображаться", mainPage.isSaucesSectionTitleDisplayed());
        assertTrue("Должны присутствовать элементы соусов", mainPage.getSaucesCount() > 0);

    }

    @Test
    @DisplayName("Переход к разделу «Начинки»")
    @Description("Проверка перехода к разделу начинок в конструкторе")
    public void fillingsSectionTest() {
        mainPage.waitForPageLoad();
        mainPage.clickOnFillingButton();
        mainPage.checkToppingFillings();
        assertTrue("Раздел начинок должен быть активен", mainPage.isFillingsSectionActive());
        assertTrue("Заголовок раздела начинок должен отображаться", mainPage.isFillingsSectionTitleDisplayed());
        assertTrue("Должны присутствовать элементы начинок", mainPage.getFillingsCount() > 0);
    }
}