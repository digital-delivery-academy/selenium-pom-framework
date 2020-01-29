package uk.co.evoco.webdriver.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.evoco.webdriver.configuration.TestConfigManager;

/**
 * Utilities class providing support methods for Select Boxes
 * While this is straight forward, its less than obvious to newcomers.
 * Feel free not to use this at all and just do it with WebDriver!
 */
public final class SelectBoxUtils {

    private static final Logger logger = LoggerFactory.getLogger(SelectBoxUtils.class);

    /**
     * Selects an option that has a matching value attribute in the Options tag markup
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
     * @param selectBox active WebElement, already located
     * @param index index in order of display
     */
    public static void itemByIndex(WebElement selectBox, int index) {
        int normalisedIndex = index - 1;
        Select select = new Select(selectBox);
        select.selectByIndex(normalisedIndex);
    }

    /**
     * Selects an option by the text that is visible in the select box
     * @param selectBox active WebElement, already located
     * @param visibleText visible text in the select box (NOT the HTML value attribute)
     */
    public static void itemByVisibleText(WebElement selectBox, String visibleText) {
        Select select = new Select(selectBox);
        select.selectByVisibleText(visibleText);
    }

    /**
     * Selects an option by the text that is visible in the select box
     * @param webDriver active webdriver to use
     * @param locator locator for us to manage the lookup of the WebElements
     * @param visibleText visible text in the select box (NOT the HTML value attribute)
     */
    public static void itemByVisibleText(WebDriver webDriver, By locator, String visibleText) {
        WebElement element = webDriver.findElement(locator);
        new WebDriverWait(
                webDriver,
                TestConfigManager.getInstance().getWebDriverConfig().getWebDriverWaitTimeout())
                .until(ExpectedConditions.elementToBeClickable(element));
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
    }

    /**
     *
     * @param webDriver active WebDriver
     * @param locator locator for us to manage the lookup of the WebElements
     * @param visibleText visible text in the select box (NOT the HTML value attribute)
     * @param waitTimeBeforeRetry time to wait before we retry
     * @throws InterruptedException because there's a Thread.sleep here
     */
    public static void tolerantSelectBoxByVisibleText(
            WebDriver webDriver, By locator, String visibleText, int waitTimeBeforeRetry) throws InterruptedException {
        try {
            itemByVisibleText(webDriver, locator, visibleText);
        } catch (Exception e) {
            logger.error("Encountered an issue while trying to select visible text from select box, will check " +
                    "to see if we tolerate this exception. Debug this issue to make your tests more stable. " +
                    "Stacktrace follows.");
            e.printStackTrace();
            for (String exceptionToHandle :
                    TestConfigManager.getInstance().getWebDriverConfig().getExceptionsToHandleOnTolerantActions()) {
                if (e.getClass().getName().contains(exceptionToHandle)) {
                    logger.error(
                            "Exception {} is tolerated, retrying after a {} second wait",
                            waitTimeBeforeRetry,
                            exceptionToHandle);
                    Thread.sleep(waitTimeBeforeRetry);
                    logger.error("Waited {} seconds after {}, now retrying", waitTimeBeforeRetry, exceptionToHandle);
                    itemByVisibleText(webDriver, locator, visibleText);
                }
            }
        }
    }
}
