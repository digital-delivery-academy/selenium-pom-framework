package uk.co.evoco.webdriver.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.evoco.webdriver.configuration.TestConfigManager;

public class SendKeysUtils {

    private static final Logger logger = LoggerFactory.getLogger(RadioButtonUtils.class);

    /**
     *
     * @param webDriver active WebDriver
     * @param locator locator for us to manage the lookup of the WebElements
     * @param textToType the text to type in the targeted WebElement
     * @param waitTimeBeforeRetry time to wait before we retry
     * @throws InterruptedException because there's a Thread.sleep here
     */
    public static void tolerantType(
            WebDriver webDriver, By locator, String textToType, int waitTimeBeforeRetry) throws InterruptedException {
        try {
            new WebDriverWait(
                    webDriver, TestConfigManager.getInstance().getWebDriverConfig().getWebDriverWaitTimeout())
                    .until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(textToType);
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
                    new WebDriverWait(
                            webDriver, TestConfigManager.getInstance().getWebDriverConfig().getWebDriverWaitTimeout())
                            .until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(textToType);
                }
            }
        }
    }

    /**
     *
     * @param webDriver active WebDriver
     * @param element WebElement to type in
     * @param textToType the text to type in the targeted WebElement
     * @param waitTimeBeforeRetry time to wait before we retry
     * @throws InterruptedException because there's a Thread.sleep here
     */
    public static void tolerantType(WebDriver webDriver, WebElement element, String textToType, int waitTimeBeforeRetry) throws InterruptedException {
        try {
            new WebDriverWait(
                    webDriver, TestConfigManager.getInstance().getWebDriverConfig().getWebDriverWaitTimeout())
                    .until(ExpectedConditions.elementToBeClickable(element)).sendKeys(textToType);
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
                    new WebDriverWait(
                            webDriver, TestConfigManager.getInstance().getWebDriverConfig().getWebDriverWaitTimeout())
                            .until(ExpectedConditions.elementToBeClickable(element)).sendKeys(textToType);
                }
            }
        }
    }
}
