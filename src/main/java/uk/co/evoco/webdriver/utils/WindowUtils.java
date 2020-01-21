package uk.co.evoco.webdriver.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Handles window utilities (like scrolling etc)
 */
public class WindowUtils {

    /**
     * Scrolls a given element into the Viewport view
     * @param webDriver
     * @param webElement
     */
    public static void scrollIntoView(WebDriver webDriver, WebElement webElement) {
        JavaScriptUtils.executeString(webDriver, webElement, "arguments[0].scrollIntoView(true);");
    }
}
