package pages;

import org.openqa.selenium.By;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngridientPage {

    private static final By RECEIPT_LIST = By.xpath("//*[contains(@class, 'article-collection')]/div/a");
    private BaseFunc baseFunc;

    public IngridientPage(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;
    }

    public List<String> getReceiptsFromPage() {
        List<String> receiptsListUrl = baseFunc.getUrlList(RECEIPT_LIST);
        return receiptsListUrl;
    }

    public Map<String, Boolean> collectInfoAboutRecieptUrlOnIngridientPage(String ingredientUrl, Boolean isPresent) {
        Map<String, Boolean> ingredientPresentOnPage = new HashMap<>();
        ingredientPresentOnPage.put(ingredientUrl, isPresent);
        return ingredientPresentOnPage;
    }
}