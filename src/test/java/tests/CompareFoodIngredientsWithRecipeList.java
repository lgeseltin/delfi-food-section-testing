package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import pages.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompareFoodIngredientsWithRecipeList {
    private static final Logger LOGGER = LogManager.getLogger(CompareFoodIngredientsWithRecipeList.class);
    private static final String DESKTOP_URL = "rus.delfi.lv/";

    private BaseFunc baseFunc = new BaseFunc();
    private HomePage homePage = new HomePage(this.baseFunc);
    private FoodPage foodPage = new FoodPage(this.baseFunc);
    private RecieptPage recieptPage = new RecieptPage(this.baseFunc);
    private IngridientPage ingridientPage = new IngridientPage(this.baseFunc);

    private Map<String, Boolean> isReceiptPresentIngredientPage = new HashMap<>();

    @Before
    public void openBrowser() {
        baseFunc.goToUrl(DESKTOP_URL);
    }

    @Test
    public void checkingUrlOnMainPage() {
        WebElement foodPageBtn = homePage.getFoodPageButtonElement();
        String linkForFoodPage = homePage.saveFoodLink(foodPageBtn);
        baseFunc.goToUrl(linkForFoodPage);

        List<String> receiptListUrls = foodPage.getUrlsList();
        String linkReceiptOfTheDay = foodPage.getReceiptOfTheDay(receiptListUrls);

        baseFunc.goToUrl(linkReceiptOfTheDay);
        List<WebElement> ingredientWebElementList = recieptPage.getIngridientListFromReceiptPage();
        Map<String, String> ingredientNameAndUrl;
        ingredientNameAndUrl = recieptPage.collectIngredientsNameAndUrl(ingredientWebElementList);

        for (Map.Entry<String, String> entry : ingredientNameAndUrl.entrySet()) {
            String ingredientTitle = entry.getKey();
            String ingredientUrl = entry.getValue();
            if (!ingredientUrl.isEmpty()) {
                baseFunc.goToUrl(ingredientUrl);
                List<String> allReceiptsUrlFromIngredientPage = ingridientPage.getReceiptsFromPage();
                if (allReceiptsUrlFromIngredientPage.contains(linkReceiptOfTheDay)) {
                    //LOGGER.info("Receipt on Ingredient page is found " + ingredientUrl);
                    ingridientPage.collectInfoAboutRecieptUrlOnIngridientPage(ingredientUrl, true);
                } else {
                    //LOGGER.info("Receipt on Ingredient page is NOT found " + ingredientUrl);
                    ingridientPage.collectInfoAboutRecieptUrlOnIngridientPage(ingredientUrl, false);
                }
            } else {
                LOGGER.info("No Link on Ingredient: " + ingredientTitle);
            }
        }
        for (Map.Entry<String, Boolean> entry : isReceiptPresentIngredientPage.entrySet()) {
            String ingredientUrl = entry.getKey();
            Boolean isUrlPresent = entry.getValue();
            if (!isUrlPresent) {
                LOGGER.info(ingredientUrl + " Receipt is missing");
            }
            Assert.assertTrue("No Receipt on Ingredient Page " + ingredientUrl, isUrlPresent);
        }
    }

    @After
    public void closeBrowser() {
        baseFunc.quitDriver();
        LOGGER.info("Browser was closed successfully");
    }
}