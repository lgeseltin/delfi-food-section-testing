package pages;

import org.openqa.selenium.By;


import java.util.List;

public class IngridientPage {
    private BaseFunc baseFunc;

    public IngridientPage(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;
    }

    //собираем все линки с рецептами со страницы Ингридиента
    public List<String> getReceiptsFromPage(By receiptsElementLocator) {
        List<String> receiptsListUrl = baseFunc.getUrlList(receiptsElementLocator);
        return receiptsListUrl;
    }
}