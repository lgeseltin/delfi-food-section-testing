package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class FoodPage {
    private static final Logger LOGGER = LogManager.getLogger(FoodPage.class);
    private BaseFunc baseFunc;

    public FoodPage(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;
    }

    //можем искать наш Рецепт Дня по локатору
    public List<WebElement> getReceiptElements(By receiptLocator) {
        List<WebElement> receiptsListElements = baseFunc.getElements(receiptLocator);
        return receiptsListElements;
    }

    //сохранять ссылку на наш Рецепт Дня
    public String saveDayReceipt(WebElement receiptsListElements) {
        String receiptUrl = baseFunc.getUrl(receiptsListElements);
        LOGGER.info("Saved Link for receipt on Receipts Page" + receiptUrl);
        return receiptUrl;
    }
}
