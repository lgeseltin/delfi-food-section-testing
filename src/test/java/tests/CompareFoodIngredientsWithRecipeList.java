package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.BaseFunc;

import java.util.ArrayList;
import java.util.List;



public class CompareFoodIngredientsWithRecipeList {
    private static final Logger LOGGER = LogManager.getLogger(CompareFoodIngredientsWithRecipeList.class);
// private static final - константа
    private static final String DESKTOP_URL = "rus.delfi.lv/";
    private static final By FOOD_SECTION = By.xpath("//a[contains(@class, 'nav-link') and contains(.//span, 'Еда')]");
    private static final By TASTY_SECTION = By.xpath("//a[contains(@class, 'nav-link') and contains(.//span, 'TASTY')]");
    private static final By RECEIPT_SECTION = By.xpath("//*[@id=\"navigation-collapse\"]/ul/li[3]/a");
    //    private static final By RECEIPTS = By.xpath("//div/a[contains(@href, 'recepty')]");
    private static final By RECEIPTS = By.xpath("//div[contains(@class, 'pull-right')]//a[contains(@href, 'recepty')]");
    private static final By INGREDIENTS_LIST = By.xpath("//div[contains(@class, 'ing-list-item')]/a");
    private static final By RECEIPT_LIST = By.xpath("//*[contains(@class, 'article-collection')]/div/a");

    // Делаем экземпляр класа (наш объект) BaseFunc
    private BaseFunc baseFunc = new BaseFunc();

//объявляем массивы для будущих списков и один делаем булеан для сравнения
    List<String> ingredientsListUrls = new ArrayList<>();
    List<String> receiptListUrls = new ArrayList<>();
    List<Boolean> isReceiptIsPresentOnIngredientsPage = new ArrayList<>();

    @Before
    // public - мы видим эту штуку везде
    // void - ничего не возвращаем
    public void prepareWebpage() {
        //открываем браузер и запихиваем в него ссылку
        baseFunc.goToUrl(DESKTOP_URL);

        // ищем Web элемент по xPath для вкладки еда
        WebElement element = baseFunc.getElement(FOOD_SECTION);

        //по найденному элементу мы сохраняем линк
        String url = baseFunc.getUrl(element);

        // переходим по сохранённому линку
        baseFunc.goToUrl(url);
    }

    @Test
    public void collectReceiptIngredientsAndCompareWithReceiptList() {
        //ищем по xPath наше блюдо
        WebElement element = baseFunc.getElement(RECEIPTS);

        //сохраняем ссылку для нашего блюда
        String receiptUrl = baseFunc.getUrl(element);

        //переходим по ссылке на наше блюдо
        baseFunc.goToUrl(receiptUrl);

        //собираем список ссылок на наши ингриденты
        ingredientsListUrls = baseFunc.getUrlList(INGREDIENTS_LIST);

        //выводим в консоль найденные ссылки на ингридиенты
        baseFunc.printUrlList(ingredientsListUrls);

        //для каждой найденной ссылки ингридиентов заходим на неё и ищем нашу ссылку на рецепт
        for (String url : ingredientsListUrls) {

            //переходим на ссылку ингридиента
            baseFunc.goToUrl(url);

            //сохраняем список рецептов на странице ингридиента
            receiptListUrls = baseFunc.getUrlList(RECEIPT_LIST);

            //сравниваем, есть ли на странице ингридиента наше блюдо
            if (receiptListUrls.contains(receiptUrl)) {

                //оно присутствует на странице - добавляем булеан - True
                isReceiptIsPresentOnIngredientsPage.add(true);

                //выводим в логгер наличие рецепта на странице игридиента
                LOGGER.info("Receipt is present on page " + url);
            } else {
                isReceiptIsPresentOnIngredientsPage.add(false);
                LOGGER.info("Receipt is NOT present on page " + url);
            }
        }

        //Проверяем булеан присутствия в списке игридиентов на наличие False  параметра. Если есть хоть один - то выводим тест фэйл
        Assert.assertTrue("Receipt is not present on page", !isReceiptIsPresentOnIngredientsPage.contains(false));
    }

    @After
    public void closeBrowser() {
        LOGGER.info("Browser closed gracefully");
        baseFunc.quitDriver();
    }
}
