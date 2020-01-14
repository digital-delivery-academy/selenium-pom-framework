package uk.co.evoco.webdriver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.UUID;

/**
 * This class allows us hooks into before and afters of a lot of WebDriver internals
 * This is a great way to capture screenshots and add conditional waits to actions without making
 * tests and page objects have superfluous code to do these things.
 *
 * We're implementing a core WebDriver interface in WebDriverEventListener here, so the declarations of methods
 * that have no contents is unfortunately necessary.  It just means in our world
 * that these "empty" methods do get called, but nothing happens, they just exit.
 */
public class WebDriverListener implements WebDriverEventListener {

    private static long WEBDRIVER_WAIT_TIMEOUT = 30L;
    private static final Logger logger = LoggerFactory.getLogger(WebDriverListener.class);
    private File screenshotDirectory;

    public void setWebdriverWaitTimeout(long webDriverWaitTimeout) {
        this.WEBDRIVER_WAIT_TIMEOUT = webDriverWaitTimeout;
    }

    public void setScreenshotDirectory(File screenshotDirectory) {
        this.screenshotDirectory = screenshotDirectory;
    }

    public void beforeAlertAccept(WebDriver webDriver) {}

    public void afterAlertAccept(WebDriver webDriver) {}

    public void afterAlertDismiss(WebDriver webDriver) {}

    public void beforeAlertDismiss(WebDriver webDriver) {}

    public void beforeNavigateTo(String s, WebDriver webDriver) {}

    public void afterNavigateTo(String s, WebDriver webDriver) {}

    public void beforeNavigateBack(WebDriver webDriver) {}

    public void afterNavigateBack(WebDriver webDriver) {}

    public void beforeNavigateForward(WebDriver webDriver) {}

    public void afterNavigateForward(WebDriver webDriver) {}

    public void beforeNavigateRefresh(WebDriver webDriver) {}

    public void afterNavigateRefresh(WebDriver webDriver) {}

    /**
     * Before each webDriver.findBy or @FindBy we want to make sure that we are supply elements
     * that are present in the DOM.  This ensures that we avoid, to some degree, StaleElementExceptions (although
     * this is generally not the best way to avoid those types of exceptions).
     * @param by
     * @param webElement
     * @param webDriver
     */
    public void beforeFindBy(By by, WebElement webElement, WebDriver webDriver) {
        new WebDriverWait(webDriver, WEBDRIVER_WAIT_TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void afterFindBy(By by, WebElement webElement, WebDriver webDriver) {}

    /**
     * Before we interact with any methods (which is anytime we click() on anything) we want to do a state
     * check that the element is actually clickable.  This ensures that elements that are disabled or not visible are
     * given time (as little as they need) to be ready to be interacted with.
     *
     * A valid case for this is where a button may be disabled until form fields are automatically valid.
     * @param webElement
     * @param webDriver
     */
    public void beforeClickOn(WebElement webElement, WebDriver webDriver) {
        new WebDriverWait(webDriver, WEBDRIVER_WAIT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public void afterClickOn(WebElement webElement, WebDriver webDriver) {}

    public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {}

    public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {}

    public void beforeScript(String s, WebDriver webDriver) {}

    public void afterScript(String s, WebDriver webDriver) {}

    public void beforeSwitchToWindow(String s, WebDriver webDriver) {}

    public void afterSwitchToWindow(String s, WebDriver webDriver) {}

    /**
     * If we have an exception, lets create a screenshot so we can see the page as it happened.
     * @param throwable
     * @param webDriver
     */
    public void onException(Throwable throwable, WebDriver webDriver) {
        File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        try {
            String filename = new StringBuilder(UUID.randomUUID().toString())
                    .append("-FAILED-")
                    .append(throwable.getClass().getName())
                    .append(".jpeg")
                    .toString();

            FileUtils.copyFile(scrFile, new File(screenshotDirectory.getPath() + "/" + filename));
        } catch (Exception e) {
            logger.error("Unable to Save to directory: {}", screenshotDirectory.getPath());
        }
    }

    public <X> void beforeGetScreenshotAs(OutputType<X> outputType) {}

    public <X> void afterGetScreenshotAs(OutputType<X> outputType, X x) {}

    public void beforeGetText(WebElement webElement, WebDriver webDriver) {}

    public void afterGetText(WebElement webElement, WebDriver webDriver, String s) {}

}