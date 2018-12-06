package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import java.util.List;

public class FoodPage {

    private static final By RECEIPT = By.xpath("//div[contains(@class, 'pull-right')]//a[contains(@href, 'recepty')]");
    private static final Logger LOGGER = LogManager.getLogger(FoodPage.class);
    private BaseFunc baseFunc;

    public FoodPage(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;
    }

    public List<String> getUrlsList() {
        List<String> urlsList = baseFunc.getUrlList(RECEIPT);
        return urlsList;
    }

    public String getReceiptOfTheDay(List<String> receiptUrlList) {
        String receiptOfTheDayUrl = receiptUrlList.get(0);
        LOGGER.info("Receipt of the day is: " + receiptOfTheDayUrl);
        return receiptOfTheDayUrl;
    }
}