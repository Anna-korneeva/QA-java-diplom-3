package page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {
    private final WebDriver driver;
    private final By activeTab = By.xpath("//div[contains(@class, 'tab_tab_type_current__2BEPc')]");
    private final By tabLocator = By.xpath("//div[contains(@class, 'tab_tab__1SPyG')]");
    private final By ingredientsList = By.className("BurgerIngredients_ingredients__list__2A-mT");
    private final By ingredientItem = By.className("BurgerIngredient_ingredient__1TVf6");
    private final By header = (By.xpath("//h2[@class='text text_type_main-medium mb-6 mt-10']"));
    private final By tabContainer = (By.xpath("//div[contains(@style, 'display: flex')]"));

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Клик по надписи 'Булки'")
    public void clickOnBunsButton() {
        clickTab("Булки");
        waitShort();
    }

    @Step("Клик по надписи 'Соусы'")
    public void clickOnSaucesButton() {
        clickTab("Соусы");
        waitShort();
    }

    @Step("Клик по надписи 'Начинки'")
    public void clickOnFillingButton() {
        clickTab("Начинки");
        waitShort();
    }

    @Step("Клик по вкладке")
    private void clickTab(String tabName) {
        System.out.println("Попытка клика на таб: " + tabName);

        // Сначала скроллим к вкладкам, если нужно
        scrollToTabs();

        By tabXpath = By.xpath("//div[contains(@class, 'tab_tab__1SPyG')]//span[text()='" + tabName + "']/..");

        WebElement tab = new WebDriverWait(driver, Duration.ofSeconds(70))
                .until(ExpectedConditions.elementToBeClickable(tabXpath));

        // Используем JavaScript для клика, чтобы избежать перехвата
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);

        // Добавляем задержку после клика
        waitShort();

        // Ждем изменения активного таба
        waitForTabChange(tabName);
        waitShort();
    }

    @Step("Находим контейнер с вкладками и скроллим к нему")
    private void scrollToTabs() {
        WebElement tabsContainer = driver.findElement(tabContainer);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tabsContainer);
        waitShort();
    }

    @Step("Ожидаем активации вкладки")
    private void waitForTabChange(String expectedTabName) {
        System.out.println("Ожидаем активации таба: " + expectedTabName);

        new WebDriverWait(driver, Duration.ofSeconds(70))
                .until(ExpectedConditions.textToBePresentInElementLocated(activeTab, expectedTabName));

        System.out.println("Таб '" + expectedTabName + "' успешно активирован");
        waitShort();
    }

    @Step("Проверяем активность вкладки, заголовок секции, наличие элеменов (Булки)")
    public void checkToppingBun() {
        // Проверяем активность вкладки
        waitForTabActivation("Булки");

        // Проверяем заголовок секции
        checkSectionTitle("Булки");

        // Проверяем наличие элементов булок
        checkIngredientsPresence("Булки", 2);
        waitShort();
    }

    @Step("Проверяем активность вкладки, заголовок секции, наличие элеменов (Соусы)")
    public void checkToppingSauce() {
        // Проверяем активность вкладки
        waitForTabActivation("Соусы");

        // Проверяем заголовок секции
        checkSectionTitle("Соусы");

        // Проверяем наличие элементов соусов
        checkIngredientsPresence("Соусы", 2);
        waitShort();
    }

    @Step("Проверяем активность вкладки, заголовок секции, наличие элеменов (Начинки)")
    public void checkToppingFillings() {
        // Проверяем активность вкладки
        waitForTabActivation("Начинки");

        // Проверяем заголовок секции
        checkSectionTitle("Начинки");

        // Проверяем наличие элементов начинок
        checkIngredientsPresence("Начинки", 2);
        waitShort();
    }

    @Step("Добавили задержку")
    private void waitForTabActivation(String expectedTabName) {
        // Добавляем задержку перед проверкой
        waitShort();

        new WebDriverWait(driver, Duration.ofSeconds(70))
                .until(ExpectedConditions.textToBePresentInElementLocated(activeTab, expectedTabName));

        WebElement active = driver.findElement(activeTab);
        String actualText = active.getText();

        if (!actualText.equals(expectedTabName)) {
            throw new AssertionError("Активен таб: " + actualText + ", а должен быть '" + expectedTabName + "'");
        }

        System.out.println("Проверка активности таба: '" + expectedTabName + "' - УСПЕХ");
        waitShort();
    }

    @Step("Ищем все заголовки и выбираем тот, который отображается")
    private void checkSectionTitle(String expectedTitle) {

        List<WebElement> allTitles = driver.findElements(header);
        boolean titleFound = false;

        for (WebElement title : allTitles) {
            if (title.isDisplayed() && title.getText().equals(expectedTitle)) {
                titleFound = true;
                System.out.println("Проверка заголовка '" + expectedTitle + "' - УСПЕХ");
                break;
            }
        }

        if (!titleFound) {
            throw new AssertionError("Не найден заголовок секции: " + expectedTitle);
        }
        waitShort();
    }

    @Step("Проверяем наличие ингридиентов")
    private void checkIngredientsPresence(String sectionType, int minElementsCount) {
        // Сначала скроллим к секции
        scrollToSection(sectionType);

        // Ждем появления списка ингредиентов
        WebElement ingredientsContainer = new WebDriverWait(driver, Duration.ofSeconds(70))
                .until(ExpectedConditions.visibilityOfElementLocated(ingredientsList));

        // Ищем элементы ингредиентов внутри контейнера
        List<WebElement> ingredients = ingredientsContainer.findElements(ingredientItem);

        if (ingredients.size() < minElementsCount) {
            throw new AssertionError("В секции '" + sectionType + "' найдено " + ingredients.size() +
                    " элементов, а ожидалось минимум " + minElementsCount);
        }

        System.out.println("В секции '" + sectionType + "' найдено " + ingredients.size() + " элементов");

        // Проверяем первые несколько элементов
        int visibleCount = 0;
        for (int i = 0; i < Math.min(minElementsCount, ingredients.size()); i++) {
            WebElement ingredient = ingredients.get(i);
            if (ingredient.isDisplayed()) {
                visibleCount++;
            }
        }

        if (visibleCount < minElementsCount) {
            throw new AssertionError("В секции '" + sectionType + "' отображается только " + visibleCount + " элементов из " + minElementsCount);
        }

        System.out.println("Проверка наличия элементов в секции '" + sectionType + "' - УСПЕХ");
        waitShort();
    }

    @Step("Скроллим к заголовку секции")
    private void scrollToSection(String sectionType) {

        By sectionTitleLocator = By.xpath("//h2[text()='" + sectionType + "']");
        WebElement sectionTitle = new WebDriverWait(driver, Duration.ofSeconds(70))
                .until(ExpectedConditions.presenceOfElementLocated(sectionTitleLocator));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", sectionTitle);
        waitShort();
    }

    @Step("Ожидаем загрузки")
    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(70))
                .until(webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
        waitShort();
    }

    // Вспомогательный метод для явной задержки 5 секунд
    private void waitShort() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(driver -> {
                    // Ожидаем любой элемент, который всегда присутствует на странице
                    return driver.findElement(By.tagName("body")).isDisplayed();
                });
    }
}