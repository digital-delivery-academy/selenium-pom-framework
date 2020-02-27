package uk.co.evoco.webdriver.utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import uk.co.evoco.webdriver.configuration.TestConfigManager;

import java.util.Optional;

/**
 * Utilities class providing support methods for Select Boxes
 * While this is straight forward, its less than obvious to newcomers.
 * Feel free not to use this at all and just do it with WebDriver!
 */
public final class SelectBoxUtils extends TolerantInteraction {

    /**
     * Selects an option that has a matching value attribute in the Options tag markup
     *
     * @param selectBox active WebElement, already located
     * @param htmlValue HTML value attribute
     */
    public static void itemByHtmlValueAttribute(WebElement selectBox, String htmlValue) {
        Select select = new Select(selectBox);
        select.selectByValue(htmlValue);
    }

    /**
     * Selects an option by the index of the option in the list.
     * The input is NOT zero based, we're normalising the input internally.
     *
     * @param selectBox active WebElement, already located
     * @param index     index in order of display
     */
    public static void itemByIndex(WebElement selectBox, int index) {
        int normalisedIndex = index - 1;
        Select select = new Select(selectBox);
        select.selectByIndex(normalisedIndex);
    }

    /**
     * Selects an option by the text that is visible in the select box
     *
     * @param selectBox   active WebElement, already located
     * @param visibleText visible text in the select box (NOT the HTML value attribute)
     */
    public static void itemByVisibleText(WebElement selectBox, String visibleText) {
        Select select = new Select(selectBox);
        select.selectByVisibleText(visibleText);
    }

    /**
     * @param webElement active WebElement, already located
     * @param htmlValue  HTML value attribute
     * @param timeout    time in seconds to keep trying
     * @throws Throwable any unhandled or un-tolerated exception
     */
    public static void tolerantItemByHtmlValueAttribute(WebElement webElement, String htmlValue, int timeout)
            throws Throwable {
        new SendKeysUtils().tolerantInteraction(
                webElement, SelectBoxInteractionType.BY_VALUE, Optional.of(htmlValue), Optional.empty(), timeout);
    }

    /**
     * @param webElement  active WebElement, already located
     * @param visibleText visible text in the select box (NOT the HTML value attribute)
     * @param timeout     time in seconds to keep trying
     * @throws Throwable any unhandled or un-tolerated exception
     */
    public static void tolerantItemByVisibleText(WebElement webElement, String visibleText, int timeout)
            throws Throwable {
        new SendKeysUtils().tolerantInteraction(
                webElement, SelectBoxInteractionType.BY_VISIBLE_TEXT,
                Optional.of(visibleText), Optional.empty(), timeout);
    }

    /**
     * @param webElement active WebElement, already located
     * @param index      index in order of display
     * @param timeout    time in seconds to keep trying
     * @throws Throwable any unhandled or un-tolerated exception
     */
    public static void tolerantItemByIndex(WebElement webElement, int index, int timeout) throws Throwable {
        new SendKeysUtils().tolerantInteraction(
                webElement, SelectBoxInteractionType.BY_INDEX, Optional.empty(), Optional.of(index), timeout);
    }

    public static void tolerantItemByVisibleText(WebElement webElement, String visibleText)
            throws Throwable {
        new SendKeysUtils().tolerantInteraction(
                webElement, SelectBoxInteractionType.BY_VISIBLE_TEXT,
                Optional.of(visibleText), Optional.empty(), TestConfigManager.get().getExceptionsWaitTimeOut());
    }

}
