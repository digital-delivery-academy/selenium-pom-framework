package uk.co.evoco.webdriver.utils;

import com.codahale.metrics.Timer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import uk.co.evoco.metrics.MetricRegistryHelper;
import uk.co.evoco.webdriver.configuration.TestConfigHelper;

import java.util.Optional;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * Utilities class providing support methods for Select Boxes
 * While this is straight forward, its less than obvious to newcomers.
 * Feel free not to use this at all and just do it with WebDriver!
 */
public final class SelectBoxUtils extends TolerantInteraction {

    private static final Timer itemByHtmlValueAttributeAction = MetricRegistryHelper.get().timer(name("SelectBoxUtils.itemByHtmlValueAttribute"));
    private static final Timer itemByIndexAction = MetricRegistryHelper.get().timer(name("SelectBoxUtils.itemByIndex"));
    private static final Timer itemByVisibleTextAction = MetricRegistryHelper.get().timer(name("SelectBoxUtils.itemByVisibleText"));
    private static final Timer tolerantItemByHtmlValueAttributeAction = MetricRegistryHelper.get().timer(name("SelectBoxUtils.tolerantItemByHtmlValueAttribute"));
    private static final Timer tolerantItemByVisibleTextAction = MetricRegistryHelper.get().timer(name("SelectBoxUtils.tolerantItemByVisibleText"));
    private static final Timer tolerantItemByIndexAction = MetricRegistryHelper.get().timer(name("SelectBoxUtils.tolerantItemByIndex"));

    /**
     * Selects an option that has a matching value attribute in the Options tag markup
     * @param selectBox active WebElement, already located
     * @param htmlValue HTML value attribute
     */
    public static void itemByHtmlValueAttribute(WebElement selectBox, String htmlValue) {
        try(final Timer.Context ignored = itemByHtmlValueAttributeAction.time()) {
            Select select = new Select(selectBox);
            select.selectByValue(htmlValue);
        }
    }

    /**
     * Selects an option by the index of the option in the list.
     * The input is NOT zero based, we're normalising the input internally.
     *
     * @param selectBox active WebElement, already located
     * @param index     index in order of display
     */
    public static void itemByIndex(WebElement selectBox, int index) {
        try(final Timer.Context ignored = itemByIndexAction.time()) {
            int normalisedIndex = index - 1;
            Select select = new Select(selectBox);
            select.selectByIndex(normalisedIndex);
        }
    }

    /**
     * Selects an option by the text that is visible in the select box
     *
     * @param selectBox   active WebElement, already located
     * @param visibleText visible text in the select box (NOT the HTML value attribute)
     */
    public static void itemByVisibleText(WebElement selectBox, String visibleText) {
        try(final Timer.Context ignored = itemByVisibleTextAction.time()) {
            Select select = new Select(selectBox);
            select.selectByVisibleText(visibleText);
        }
    }

    /**
     * @param webElement active WebElement, already located
     * @param htmlValue  HTML value attribute
     * @param timeout    time in seconds to keep trying
     * @throws Throwable any unhandled or un-tolerated exception
     */
    public static void tolerantItemByHtmlValueAttribute(WebElement webElement, String htmlValue, int timeout)
            throws Throwable {
        try(final Timer.Context ignored = tolerantItemByHtmlValueAttributeAction.time()) {
            new SelectBoxUtils().tolerantInteraction(
                    webElement, SelectBoxInteractionType.BY_VALUE, Optional.of(htmlValue), Optional.empty(), timeout);
        }
    }

    /**
     * @param webElement active WebElement, already located
     * @param htmlValue  HTML value attribute
     * @throws Throwable any unhandled or un-tolerated exception
     */
    public static void tolerantItemByHtmlValueAttribute(WebElement webElement, String htmlValue)
            throws Throwable {
        try(final Timer.Context ignored = tolerantItemByHtmlValueAttributeAction.time()) {
            new SelectBoxUtils().tolerantInteraction(
                    webElement, SelectBoxInteractionType.BY_VALUE, Optional.of(htmlValue), Optional.empty(),
                    TestConfigHelper.get().getTolerantActionWaitTimeoutInSeconds());
        }
    }

    /**
     * @param webElement  active WebElement, already located
     * @param visibleText visible text in the select box (NOT the HTML value attribute)
     * @param timeout     time in seconds to keep trying
     * @throws Throwable any unhandled or un-tolerated exception
     */
    public static void tolerantItemByVisibleText(WebElement webElement, String visibleText, int timeout)
            throws Throwable {
        try(final Timer.Context ignored = tolerantItemByVisibleTextAction.time()) {
            new SelectBoxUtils().tolerantInteraction(
                    webElement, SelectBoxInteractionType.BY_VISIBLE_TEXT,
                    Optional.of(visibleText), Optional.empty(), timeout);
        }
    }

    /**

     * @param webElement active WebElement, already located
     * @param visibleText visible text in the select box (NOT the HTML value attribute)
     * @throws Throwable any unhandled or un-tolerated exception
     */
    public static void tolerantItemByVisibleText(WebElement webElement, String visibleText)
            throws Throwable {
        try(final Timer.Context ignored = tolerantItemByVisibleTextAction.time()) {
            new SelectBoxUtils().tolerantInteraction(
                    webElement, SelectBoxInteractionType.BY_VISIBLE_TEXT,
                    Optional.of(visibleText), Optional.empty(),
                    TestConfigHelper.get().getTolerantActionWaitTimeoutInSeconds());
        }
    }

    /**
     * @param webDriver
     * @param locator
     * @param visibleText
     * @throws Throwable
     */
    public static void tolerantItemByVisibleText(WebDriver webDriver, By locator, String visibleText)
            throws Throwable {
        try(final Timer.Context ignored = tolerantItemByVisibleTextAction.time()) {
            new SelectBoxUtils().tolerantInteraction(
                    webDriver,
                    locator, SelectBoxInteractionType.BY_VISIBLE_TEXT,
                    Optional.of(visibleText), Optional.empty(),
                    TestConfigHelper.get().getTolerantActionWaitTimeoutInSeconds());
        }
    }

    /**
     * @param webElement active WebElement, already located
     * @param index      index in order of display
     * @param timeout    time in seconds to keep trying
     * @throws Throwable any unhandled or un-tolerated exception
     */
    public static void tolerantItemByIndex(WebElement webElement, int index, int timeout) throws Throwable {
        try(final Timer.Context ignored = tolerantItemByIndexAction.time()) {
            new SelectBoxUtils().tolerantInteraction(
                    webElement, SelectBoxInteractionType.BY_INDEX, Optional.empty(), Optional.of(index), timeout);
        }
    }

    /**
     * @param webElement active WebElement, already located
     * @param index      index in order of display
     * @throws Throwable any unhandled or un-tolerated exception
     */
    public static void tolerantItemByIndex(WebElement webElement, int index) throws Throwable {
        try(final Timer.Context ignored = tolerantItemByIndexAction.time()) {
            new SelectBoxUtils().tolerantInteraction(
                    webElement, SelectBoxInteractionType.BY_INDEX, Optional.empty(), Optional.of(index),
                    TestConfigHelper.get().getTolerantActionWaitTimeoutInSeconds());
        }
    }
}
