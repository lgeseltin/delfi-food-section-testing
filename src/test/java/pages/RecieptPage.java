package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class RecieptPage {
    private static final Logger LOGGER = LogManager.getLogger(RecieptPage.class);
    private BaseFunc baseFunc;

    public RecieptPage(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;
    }

    //ищем элементы ингридиентов на странице по xPath и сохраняем их в список
    public List<WebElement> getIngridientListFromReceipt(By ingridientLocator) {
        List<WebElement> ingredientList = baseFunc.getElements(ingridientLocator);
        return ingredientList;
    }

    public List<String> getIngridientUrlListFromReceiptPage(By ingridientLocator){
        List<String> ingedientUrlList = baseFunc.getUrlList(ingridientLocator);
        return ingedientUrlList;
    }

}