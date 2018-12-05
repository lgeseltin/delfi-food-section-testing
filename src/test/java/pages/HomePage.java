package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HomePage {
    private static final Logger LOGGER = LogManager.getLogger(HomePage.class);
    private BaseFunc baseFunc;

    public HomePage(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;
    }

    //можем искать нашу кнопку ЕДА по локатору
    public WebElement getFoodPageButtonElement(By foodLocator) {
        WebElement foodPageButtonElement = baseFunc.getElement(foodLocator);
        return foodPageButtonElement;
    }

    //можем сохранять линк по найденному локатору
    public String saveFoodLink(WebElement foodPageButtonElement) {
        String url = baseFunc.getUrl(foodPageButtonElement);
        LOGGER.info("Saved Link for FoodPageButton on Home Page " + url);
        return url;
    }
}