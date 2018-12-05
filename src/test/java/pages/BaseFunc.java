package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverInfo;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaseFunc {
    public WebDriver driver;
    private WebDriverWait wait;

    private static final String CHROME_DRIVER_LINUX64 = "./drivers/linux/x64/Chrome/chromedriver";
    private static final String FIREFOX_DRIVER_LINUX64 = "./drivers/linux/x64/Firefox/geckodriver";
    private static final String CHROME_DRIVER_WIN32 = "./drivers/windows/win32/Chrome/chromedriver.exe";
    private static final String FIREFOX_DRIVER_WIN64 = "./drivers/windows/win64/Firefox/geckodriver.exe";

    private static final String GECKO_PROPERTY = "webdriver.gecko.driver";
    private static final String CHROME_PROPERTY = "webdriver.chrome.driver";

    private static final Logger LOGGER = LogManager.getLogger(BaseFunc.class);

    //конструктор
    public BaseFunc() {
        this.initDriver();
    }

    public void initDriver() {
        System.setProperty(CHROME_PROPERTY, CHROME_DRIVER_LINUX64);
        this.driver = new ChromeDriver();
        LOGGER.info("Starting WEB Browser");
        wait = new WebDriverWait(driver, 5);
        driver.manage().window().maximize();
    }

    public String getUrl(WebElement element) {
        String url = element.getAttribute("href");
        LOGGER.info("Finding " + url);
        return url;
    }

    public List<String> getUrlList(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        List<String> urlsList = new ArrayList<>();
        for (WebElement element : elements) {
            urlsList.add(element.getAttribute("href"));
        }
        return urlsList;

    }

    public void printUrlList(List<String> urlsList) {
        for (String url : urlsList) {
            LOGGER.info("Printing article URL : " + url);
        }
    }

    public void goToUrl(String url) {
        if (!url.contains("http://") && !url.contains("https://")) {
            url = "http://" + url;
        }
        LOGGER.info("Opening " + url);
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    public List<WebElement> getElements(By locator) {
        LOGGER.info("Getting elements: " + locator);
        return driver.findElements(locator);
    }

    public WebElement getElement(By locator) {
        LOGGER.info("Getting element: " + locator);
        return driver.findElement(locator);
    }

    public void click(By locator) {
        LOGGER.info("Clicking on element: ");
        driver.findElement(locator).click();
    }

    public void quitDriver() {
        driver.quit();
    }
}
