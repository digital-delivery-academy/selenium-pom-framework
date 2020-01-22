package uk.co.evoco.webdriver.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Handles window utilities (like scrolling etc)
 */
public final class WindowUtils {

    /**
     * Scrolls a given element into the Viewport view
     * @param webDriver active WebDriver instance
     * @param webElement active WebElement, already located
     */
    public static void scrollIntoView(WebDriver webDriver, WebElement webElement) {
        JavaScriptUtils.executeString(webDriver, webElement, "arguments[0].scrollIntoView(true);");
    }
}
