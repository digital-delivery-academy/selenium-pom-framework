package uk.co.evoco.webdriver.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.evoco.webdriver.configuration.TestConfigManager;

import java.util.List;

/**
 * Only for us in the situations outlined for the provided methods.
 * There's nothing wrong with WebDrivers normal click method, if you don't need this, steer well clear.
 */
public final class ClickUtils {

    private static final Logger logger = LoggerFactory.getLogger(ClickUtils.class);

    /**
     * Use this method if you're experiencing exceptions that are UN-FIXABLE in your application
     * and you find that you're getting intermittent (and unhelpful) exceptions.
     *
     * This method takes its configuration from the configuration file (see documentation for how to add exceptions
     * to the list of exceptions that this method will tolerate).
     *
     * It retries only once and waits for 3 seconds.  This may become configurable in the future.
     *
     * @param webDriver
     * @param webElement
     * @throws InterruptedException because there is a Thread.sleep in this method.
     */
    public static void tolerantClick(WebDriver webDriver, WebElement webElement) throws InterruptedException {
        try {
            click(webDriver, webElement, TestConfigManager.getInstance().getWebDriverConfig().getWebDriverWaitTimeout());
        } catch(Exception e) {
            logger.error("Encountered an issue while trying to click, will check to see if we tolerate this exception. Debug this issue to make your tests more stable. Stacktrace follows.");
            e.printStackTrace();
            for(String exceptionToHandle : TestConfigManager.getInstance().getWebDriverConfig().getExceptionsToHandleOnTolerantActions()) {
                if(e.getClass().getName().contains(exceptionToHandle)) {
                    logger.error("Exception {} is tolerated, retrying after a 3 second wait", exceptionToHandle);
                    Thread.sleep(3);
                    logger.error("Waited 3 seconds after {}, now retrying", exceptionToHandle);
                    click(webDriver, webElement, TestConfigManager.getInstance().getWebDriverConfig().getWebDriverWaitTimeout());
                }
            }
            // Can't handle the exception with a retry so re-throwing the exception
            throw e;
        }
    }

    private static void click(WebDriver webDriver, WebElement webElement, long timeout) {
        new WebDriverWait(webDriver, timeout).until(ExpectedConditions.elementToBeClickable(webElement)).click();
    }
}