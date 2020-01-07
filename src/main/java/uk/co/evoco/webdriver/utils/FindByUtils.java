package uk.co.evoco.webdriver.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class FindByUtils {

    private static final Logger logger = LoggerFactory.getLogger(FindByUtils.class);

    /**
     * Finds the first element that is displayed with given locator.
     * Useful for instances where there are multiple elements on the DOM with the same locator and one (or more)
     * of them are hidden (e.g. used in mobile layouts or hold a different z-index given some other element is present)
     * @param driver
     * @param locator
     * @return
     */
    public static WebElement multipleLocatorMatchGetDisplayed(WebDriver driver, By locator) throws WebDriverException {
        List<WebElement> elements = driver.findElements(locator);
        logger.info("Found {} elements with locator: {}", elements.size(), locator.toString());
        for(WebElement element : elements) {
            if(element.isDisplayed()) {
                return element;
            }
        }
        throw new WebDriverException("No elements in the list were displayed");
    }
}

