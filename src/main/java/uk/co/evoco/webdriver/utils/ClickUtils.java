package uk.co.evoco.webdriver.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.evoco.webdriver.configuration.TestConfigManager;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

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
     * @param webDriver active WebDriver instance
     * @param webElement active WebElement, already located
     * @param waitTimeBeforeRetry time to wait explicitly before we retry
     * @throws InterruptedException because there is a Thread.sleep in this method.
     */
    public static void tolerantClick(WebDriver webDriver, WebElement webElement, int waitTimeBeforeRetry) throws InterruptedException {
        try {
            click(webDriver, webElement, TestConfigManager.get().getWebDriverWaitTimeout());
        } catch(Exception e) {
            logger.error("Encountered an issue while trying to click, will check to see if we tolerate this exception. Debug this issue to make your tests more stable. Stacktrace follows.");
            e.printStackTrace();
            for(String exceptionToHandle : TestConfigManager.get().getExceptionsToHandleOnTolerantActions()) {
                if(e.getClass().getName().contains(exceptionToHandle)) {
                    logger.error("Exception {} is tolerated, retrying after a {} second wait", waitTimeBeforeRetry, exceptionToHandle);
                    Thread.sleep(waitTimeBeforeRetry);
                    logger.error("Waited {} seconds after {}, now retrying", waitTimeBeforeRetry, exceptionToHandle);
                    click(webDriver, webElement, TestConfigManager.get().getWebDriverWaitTimeout());
                }
            }
        }
    }

    /**
     *
     * Use this method if you're experiencing exceptions that are UN-FIXABLE in your application
     * and you find that you're getting intermittent (and unhelpful) exceptions.
     *
     * This method takes its configuration from the configuration file (see documentation for how to add exceptions
     * to the list of exceptions that this method will tolerate).
     *
     * It retries only once and waits for 3 seconds.  This may become configurable in the future.
     *
     * @param webDriver active WebDriver instance
     * @param locator locator we will use to re-lookup the element on retry
     * @param waitTimeBeforeRetry time to wait explicitly before we retry
     * @throws InterruptedException
     */
    public static void tolerantClick(WebDriver webDriver, By locator, int waitTimeBeforeRetry) throws InterruptedException {
        try {
            click(webDriver, locator, TestConfigManager.get().getWebDriverWaitTimeout());
        } catch(Exception e) {
            logger.error("Encountered an issue while trying to click, will check to see if we tolerate this exception. Debug this issue to make your tests more stable. Stacktrace follows.");
            e.printStackTrace();
            for(String exceptionToHandle : TestConfigManager.get().getExceptionsToHandleOnTolerantActions()) {
                if(e.getClass().getName().contains(exceptionToHandle)) {
                    logger.error("Exception {} is tolerated, retrying after a {} second wait", waitTimeBeforeRetry, exceptionToHandle);
                    Thread.sleep(waitTimeBeforeRetry);
                    logger.error("Waited {} seconds after {}, now retrying", waitTimeBeforeRetry, exceptionToHandle);
                    click(webDriver, locator, TestConfigManager.get().getWebDriverWaitTimeout());
                }
            }
        }
    }

    /**
     *
     * @param webDriver active WebDriver instance
     * @param webElement locator we will use to re-lookup the element on retry
     * @param timeoutInSeconds time to continue trying for
     * @throws Throwable
     */
    public static void tolerantPollingClick(WebDriver webDriver, WebElement webElement, int timeoutInSeconds) throws Throwable {
        Clock clock = Clock.systemDefaultZone();
        Instant end = clock.instant().plusSeconds(timeoutInSeconds);
        Sleeper sleeper = Sleeper.SYSTEM_SLEEPER;
        Duration intervalDuration = Duration.ofMillis(500);

        Throwable lastException = null;
        while(true) {
            try {
                logger.info("Testing is element is currently clickable");
                if(Boolean.TRUE.equals(webElement.isEnabled())) {
                    logger.info("Element was found, clicking and exiting");
                    webElement.click();
                    return;
                } else {
                    logger.info("Element is currently not clickable");
                }
            } catch(Throwable e) {
                logger.info("Exception thrown {}, will check if ignored", lastException.getCause());
                lastException = propagateIfNotIgnored(e);
            }

            if(end.isBefore(clock.instant())) {
                if(null == lastException) {
                    logger.error("Exception condition failed: Timeout (tried for {} seconds with 500ms interval", timeoutInSeconds);
                    lastException = new TimeoutException();
                } else {
                    logger.error("Exception condition failed: {} (tried for {} seconds with 500ms interval",
                            lastException.getCause(), timeoutInSeconds);
                }
                throw lastException;
            }

            try {
                logger.info("Waiting before poll for {}ms", intervalDuration.toMillis());
                sleeper.sleep(intervalDuration);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new WebDriverException(e);
            }
        }
    }

    private static void click(WebDriver webDriver, WebElement webElement, long timeout) {
        new WebDriverWait(webDriver, timeout).until(ExpectedConditions.elementToBeClickable(webElement)).click();
    }

    private static void click(WebDriver webDriver, By locator, long timeout) {
        WebElement webElement = webDriver.findElement(locator);
        new WebDriverWait(webDriver, timeout).until(ExpectedConditions.elementToBeClickable(webElement)).click();
    }

    private static Throwable propagateIfNotIgnored(Throwable e) throws Throwable {
        for (String ignoredException : TestConfigManager.get().getExceptionsToHandleOnTolerantActions()) {
            if (Class.forName(ignoredException).isInstance(e)) {
                logger.info("Exception {} will be ignored", ignoredException);
            } else {
                return e;
            }
        }
        return e;
    }
}
