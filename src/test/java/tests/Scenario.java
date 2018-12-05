package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scenario {
    private static final Logger LOGGER = LogManager.getLogger(CompareFoodIngredientsWithRecipeList.class);
    // private static final - константа
    private static final String DESKTOP_URL = "rus.delfi.lv/";
    private static final By FOOD_SECTION = By.xpath("//a[contains(@class, 'nav-link') and contains(.//span, 'Еда')]");
    //private static final By TASTY_SECTION = By.xpath("//a[contains(@class, 'nav-link') and contains(.//span, 'TASTY')]");
    //private static final By RECEIPT_SECTION = By.xpath("//*[@id=\"navigation-collapse\"]/ul/li[3]/a");
    private static final By RECEIPT = By.xpath("//div[contains(@class, 'pull-right')]//a[contains(@href, 'recepty')]");
    private static final By INGREDIENTS_LIST = By.xpath("//div[contains(@class, 'ing-list-item')]/a");
    private static final By RECEIPT_LIST = By.xpath("//*[contains(@class, 'article-collection')]/div/a");

    // Делаем экземпляр класа (наш объект) BaseFunc
    private BaseFunc baseFunc = new BaseFunc();
    private HomePage homePage = new HomePage(this.baseFunc);
    private FoodPage foodPage = new FoodPage(this.baseFunc);
    private RecieptPage recieptPage = new RecieptPage(this.baseFunc);
    private IngridientPage ingridientPage = new IngridientPage(this.baseFunc);

    //объявляем массивы для будущих списков и один делаем булеан для сравнения
    List<String> ingredientsListUrls = new ArrayList<>();
    List<String> receiptListUrls = new ArrayList<>();
    List<Boolean> isReceiptIsPresentOnIngredientsPage = new ArrayList<>();
    List<Boolean> missingUrlToIngridient = new ArrayList<>();
    String linkForFoodPage, linkReceiptOfTheDay;

    @Before
    public void openBrowser() {
        baseFunc.goToUrl(DESKTOP_URL);
    }

    @Test
    //Проверяем наличие линка на кнопку Еда (пустой ли урл?) из МэйнПэйдж
    public void checkingUrlOnMainPage() {
        WebElement foodPageBtn = homePage.getFoodPageButtonElement(FOOD_SECTION);
        linkForFoodPage = homePage.saveFoodLink(foodPageBtn);
        baseFunc.goToUrl((linkForFoodPage));
        //Assert.assertNotEquals(" ", linkForFoodPage);
        // }

        //Проверяем не пустой ли линк на Рецепт дня (данные берём из ФуудПэйдж)
        List<String> receiptListUrls = baseFunc.getUrlList(RECEIPT);
        linkReceiptOfTheDay = receiptListUrls.get(0);
        LOGGER.info("Receipt of the day is: " + linkReceiptOfTheDay);
        //Assert.assertNotEquals(" ", linkReceiptOfTheDay);
        // }


        //заходим на страницу сохранённого рецепта
        baseFunc.goToUrl(linkReceiptOfTheDay);
        //находим наш список по xPath (имя ингредиента)
        //сохраяем полученный список в список Игредиентов
        List<String> allIngredientsList = baseFunc.getUrlList(INGREDIENTS_LIST);
        List<String> allReceptsUrlFromIngredientPage = new ArrayList<>();
        Map<String, Boolean> isReceptPresentIngridientPage = new HashMap<String, Boolean>();
        //проверяем нет ли пустых полей в списке ингридиентов
        for (String ingredientUrl : allIngredientsList) {
            if (!ingredientUrl.isEmpty()) {
                baseFunc.goToUrl(ingredientUrl);
                //сохраняем список URL всех рецептов на странице по xPath RECEIPT_LIST
                allReceptsUrlFromIngredientPage = ingridientPage.getReceiptsFromPage(RECEIPT_LIST);
                //и сравниваем есть ли на странице наш адрес рецепта
                if (allReceptsUrlFromIngredientPage.contains(linkReceiptOfTheDay)) {
                    // если есть - выводим сообщение *Рецепт блюда на странице ингредиента найден*
                    //LOGGER.info("Receipt on Ingredient page is found " + ingredientUrl);
                    ///////добавляем параметр
                    /////////   True
                    isReceptPresentIngridientPage.put(ingredientUrl, true);

                } else {
                    // если нет - выводим сообщение *Рецепт блюда на странице ингредиента(имя ингридиента) не найден)
                    //LOGGER.info("Receipt on Ingredient page is NOT found " + ingredientUrl);
                    ///////добавляем параметр
                    /////////  False
                    isReceptPresentIngridientPage.put(ingredientUrl, false);
                }
            } else {
                LOGGER.info("Ingredient's URL is empty " + ingredientUrl);
            }
        }
        for(Map.Entry<String,Boolean> entry : isReceptPresentIngridientPage.entrySet()){
            String ingridientUrl = entry.getKey();
            Boolean isUrlPresent = entry.getValue();
            if(!isUrlPresent){
                LOGGER.info(ingridientUrl + " Recept is missing");
            }
            Assert.assertTrue("No Receipt on Ingredient Page " + ingridientUrl, isUrlPresent);

        }
    }

    @After
    public void closeBrowser() {
        baseFunc.quitDriver();
        LOGGER.info("Browser was closed successfully");
    }
}