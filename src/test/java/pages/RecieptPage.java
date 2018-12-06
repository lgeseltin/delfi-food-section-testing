package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecieptPage {

    private static final By INGREDIENTS_LIST = By.xpath("//div[contains(@class, 'ing-list-item')]/a");
    private static final Logger LOGGER = LogManager.getLogger(RecieptPage.class);
    private BaseFunc baseFunc;

    public RecieptPage(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;
    }

    public List<WebElement> getIngridientListFromReceiptPage() {
        List<WebElement> ingedientsList = baseFunc.getElements(INGREDIENTS_LIST);
        return ingedientsList;
    }

    public Map<String, String> collectIngredientsNameAndUrl(List<WebElement> ingredientList) {
        Map<String, String> ingredientNameAndUrl = new HashMap<>();
        for (WebElement ingredient : ingredientList) {
            String ingredientUrl = baseFunc.getUrl(ingredient);
            String ingredientTitle = ingredient.getText();
            ingredientNameAndUrl.put(ingredientTitle, ingredientUrl);
        }
        return ingredientNameAndUrl;
    }
}