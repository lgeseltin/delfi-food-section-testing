package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HomePage {

    private static final By FOOD_SECTION = By.xpath("//a[contains(@class, 'nav-link') and contains(.//span, 'Еда')]");
    private static final Logger LOGGER = LogManager.getLogger(HomePage.class);
    private BaseFunc baseFunc;

    public HomePage(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;
    }

    public WebElement getFoodPageButtonElement() {
        WebElement foodPageButtonElement = baseFunc.getElement(FOOD_SECTION);
        return foodPageButtonElement;
    }

    public String saveFoodLink(WebElement foodPageButtonElement) {
        String url = baseFunc.getUrl(foodPageButtonElement);
        LOGGER.info("Saved Link for FoodPageButton on Home Page " + url);
        return url;
    }
}