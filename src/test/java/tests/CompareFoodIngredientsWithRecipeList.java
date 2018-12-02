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

    private static final String DESKTOP_URL = "rus.delfi.lv/";
    private static final By FOOD_SECTION = By.xpath("//a[contains(@class, 'nav-link') and contains(.//span, 'Еда')]");
    private static final By TASTY_SECTION = By.xpath("//a[contains(@class, 'nav-link') and contains(.//span, 'TASTY')]");
    private static final By RECEIPT_SECTION = By.xpath("//*[@id=\"navigation-collapse\"]/ul/li[3]/a");
    //    private static final By RECEIPTS = By.xpath("//div/a[contains(@href, 'recepty')]");
    private static final By RECEIPTS = By.xpath("//div[contains(@class, 'pull-right')]//a[contains(@href, 'recepty')]");
    private static final By INGREDIENTS_LIST = By.xpath("//div[contains(@class, 'ing-list-item')]/a");
    private static final By RECEIPT_LIST = By.xpath("//*[contains(@class, 'article-collection')]/div/a");

    private BaseFunc baseFunc = new BaseFunc();

    List<String> ingredientsListUrls = new ArrayList<>();
    List<String> receiptListUrls = new ArrayList<>();
    List<Boolean> isReceiptIsPresentOnIngredientsPage = new ArrayList<>();

    @Before
    public void prepareWebpage() {
        baseFunc.goToUrl(DESKTOP_URL);
        WebElement element = baseFunc.getElement(FOOD_SECTION);
        String url = baseFunc.getUrl(element);
        baseFunc.goToUrl(url);
    }

    @Test
    public void collectReceiptIngredientsAndCompareWithReceiptList() {
        WebElement element = baseFunc.getElement(RECEIPTS);
        String receiptUrl = baseFunc.getUrl(element);
        baseFunc.goToUrl(receiptUrl);
        ingredientsListUrls = baseFunc.getUrlList(INGREDIENTS_LIST);
        baseFunc.printUrlList(ingredientsListUrls);
        for (String url : ingredientsListUrls) {
            baseFunc.goToUrl(url);
            receiptListUrls = baseFunc.getUrlList(RECEIPT_LIST);
            if (receiptListUrls.contains(receiptUrl)) {
                isReceiptIsPresentOnIngredientsPage.add(true);
                LOGGER.info("Recept is present on page" + url);
            } else {
                isReceiptIsPresentOnIngredientsPage.add(false);
                LOGGER.info("Recept is NOT present on page" + url);
            }
        }
        Assert.assertTrue("Receipt is not present on page", !isReceiptIsPresentOnIngredientsPage.contains(false));
    }

    @After
    public void closeBrowser() {
        LOGGER.info("Browser closed gracefully");
        baseFunc.quitDriver();
    }
}
