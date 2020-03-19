package uk.co.evoco.webdriver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.evoco.webdriver.configuration.TestConfigHelper;

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

    private static final Logger logger = LoggerFactory.getLogger(WebDriverListener.class);
    private File screenshotDirectory;

    /**
     * Sets the screenshot target directory that will be used for screenshots generated inside onException()
     * @param screenshotDirectory the path to the screenshot directory used in onException
     */
    public void setScreenshotDirectory(File screenshotDirectory) {
        this.screenshotDirectory = screenshotDirectory;
    }

    /**
     *
     * @param webDriver active WebDriver instance
     */
    public void beforeAlertAccept(WebDriver webDriver) {
        // nothing yet
    }

    /**
     *
     * @param webDriver active WebDriver instance
     */
    public void afterAlertAccept(WebDriver webDriver) {
        // nothing yet
    }

    /**
     *
     * @param webDriver active WebDriver instance
     */
    public void afterAlertDismiss(WebDriver webDriver) {
        // nothing yet
    }

    /**
     *
     * @param webDriver active WebDriver instance
     */
    public void beforeAlertDismiss(WebDriver webDriver) {
        // nothing yet
    }

    /**
     *
     * @param s String
     * @param webDriver active WebDriver instance
     */
    public void beforeNavigateTo(String s, WebDriver webDriver) {
        // nothing yet
    }

    /**
     *
     * @param s String
     * @param webDriver active WebDriver instance
     */
    public void afterNavigateTo(String s, WebDriver webDriver) {
        // nothing yet
    }

    /**
     *
     * @param webDriver active WebDriver instance
     */
    public void beforeNavigateBack(WebDriver webDriver) {
        // nothing yet
    }

    /**
     *
     * @param webDriver active WebDriver instance
     */
    public void afterNavigateBack(WebDriver webDriver) {
        // nothing yet
    }

    /**
     *
     * @param webDriver active WebDriver instance
     */
    public void beforeNavigateForward(WebDriver webDriver) {
        // nothing yet
    }

    /**
     *
     * @param webDriver active WebDriver instance
     */
    public void afterNavigateForward(WebDriver webDriver) {
        // nothing yet
    }

    /**
     *
     * @param webDriver active WebDriver instance
     */
    public void beforeNavigateRefresh(WebDriver webDriver) {
        // nothing yet
    }

    /**
     *
     * @param webDriver active WebDriver instance
     */
    public void afterNavigateRefresh(WebDriver webDriver) {
        // nothing yet
    }

    /**
     * Before each webDriver.findBy or @FindBy we want to make sure that we are supply elements
     * that are present in the DOM.  This ensures that we avoid, to some degree, StaleElementExceptions (although
     * this is generally not the best way to avoid those types of exceptions).
     * @param by locator
     * @param webElement active WebElement, already located
     * @param webDriver active WebDriver instance
     */
    public void beforeFindBy(By by, WebElement webElement, WebDriver webDriver) {
        new WebDriverWait(webDriver,
                TestConfigHelper.get().getWebDriverWaitTimeout()).until(
                        ExpectedConditions.presenceOfElementLocated(by));
    }

    /**
     *
     * @param by locator
     * @param webElement active WebElement, already located
     * @param webDriver active WebDriver instance
     */
    public void afterFindBy(By by, WebElement webElement, WebDriver webDriver) {
        // nothing yet
    }

    /**
     * Before we interact with any methods (which is anytime we click() on anything) we want to do a state
     * check that the element is actually clickable.  This ensures that elements that are disabled or not visible are
     * given time (as little as they need) to be ready to be interacted with.
     *
     * A valid case for this is where a button may be disabled until form fields are automatically valid.
     * @param webElement active WebElement, already located
     * @param webDriver active WebDriver instance
     */
    public void beforeClickOn(WebElement webElement, WebDriver webDriver) {
        new WebDriverWait(webDriver,
                TestConfigHelper.get().getWebDriverWaitTimeout()).until(
                        ExpectedConditions.elementToBeClickable(webElement));
    }

    /**
     *
     * @param webElement active WebElement, already located
     * @param webDriver active WebDriver instance
     */
    public void afterClickOn(WebElement webElement, WebDriver webDriver) {
        // nothing yet
    }

    /**
     *
     * @param webElement active WebElement, already located
     * @param webDriver active WebDriver instance
     * @param charSequences character sequence
     */
    public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
        // nothing yet
    }

    /**
     *
     * @param webElement active WebElement, already located
     * @param webDriver active WebDriver instance
     * @param charSequences character sequence
     */
    public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
        // nothing yet
    }

    /**
     *
     * @param s String
     * @param webDriver active WebDriver instance
     */
    public void beforeScript(String s, WebDriver webDriver) {
        // nothing yet
    }

    /**
     *
     * @param s String
     * @param webDriver active WebDriver instance
     */
    public void afterScript(String s, WebDriver webDriver) {
        // nothing yet
    }

    /**
     *
     * @param s String
     * @param webDriver active WebDriver instance
     */
    public void beforeSwitchToWindow(String s, WebDriver webDriver) {
        // nothing yet
    }

    /**
     *
     * @param s String
     * @param webDriver active WebDriver instance
     */
    public void afterSwitchToWindow(String s, WebDriver webDriver) {
        // nothing yet
    }

    /**
     * If we have an exception, lets create a screenshot so we can see the page as it happened.
     * @param throwable the thrown exception that we are holding here
     * @param webDriver active WebDriver instance
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

    /**
     *
     * @param outputType output type
     * @param <X> x
     */
    public <X> void beforeGetScreenshotAs(OutputType<X> outputType) {
        // nothing yet
    }

    /**
     *
     * @param outputType output type
     * @param x x
     * @param <X> x
     */
    public <X> void afterGetScreenshotAs(OutputType<X> outputType, X x) {
        // nothing yet
    }

    /**
     *
     * @param webElement active WebElement, already located
     * @param webDriver active WebDriver instance
     */
    public void beforeGetText(WebElement webElement, WebDriver webDriver) {
        // nothing yet
    }

    /**
     *
     * @param webElement active WebElement, already located
     * @param webDriver active WebDriver instance
     * @param s String
     */
    public void afterGetText(WebElement webElement, WebDriver webDriver, String s) {
        // nothing yet
    }
}