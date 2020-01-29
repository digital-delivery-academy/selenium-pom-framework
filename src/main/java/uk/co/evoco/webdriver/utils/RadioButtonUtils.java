package uk.co.evoco.webdriver.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.evoco.webdriver.configuration.TestConfigManager;

import java.util.List;

/**
 * Utility methods to select radio buttons
 */
public final class RadioButtonUtils {

    private static final Logger logger = LoggerFactory.getLogger(RadioButtonUtils.class);

    /**
     * Given a list of WebElements that locate the labels of the radio buttons,
     * finds the radio button with the given visible label text and selects it.
     * @param webElements active WebElement, already located
     * @param visibleLabelText text that is visible on the page in the label tags
     */
    public static void selectByLabel(List<WebElement> webElements, String visibleLabelText) {
        for (WebElement webElement : webElements) {
            if (webElement.getText().equals(visibleLabelText)) {
                webElement.click();
                break;
            }
        }
    }

    /**
     *
     * @param webDriver active WebDriver
     * @param locator for us to manage the lookup of the WebElement
     * @param visibleLabelText text that is visible on the page in the label tags
     */
    public static void selectByLabel(WebDriver webDriver, By locator, String visibleLabelText) {
        List<WebElement> webElements = new WebDriverWait(
                webDriver, TestConfigManager.getInstance().getWebDriverConfig().getWebDriverWaitTimeout())
                .until(ExpectedConditions.presenceOfElementLocated(locator)).findElements(locator);
        for (WebElement webElement : webElements) {
            if (webElement.getText().equals(visibleLabelText)) {
                webElement.click();
                break;
            }
        }
    }

    /**
     *
     * @param webDriver active WebDriver
     * @param locator locator for us to manage the lookup of the WebElements
     * @param visibleLabelText text that is visible on the page in the label tags
     * @param waitTimeBeforeRetry time to wait before we retry
     * @throws InterruptedException because there's a Thread.sleep here
     */
    public static void tolerantSelectByLabel(
            WebDriver webDriver, By locator, String visibleLabelText, int waitTimeBeforeRetry) throws InterruptedException {
        try {
            selectByLabel(webDriver, locator, visibleLabelText);
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
                    selectByLabel(webDriver, locator, visibleLabelText);
                }
            }
        }
    }
}
