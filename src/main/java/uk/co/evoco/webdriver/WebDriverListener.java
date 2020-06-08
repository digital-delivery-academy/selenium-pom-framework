package uk.co.evoco.webdriver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.By;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
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
 */
public class WebDriverListener extends AbstractWebDriverEventListener {

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
     * If we have an exception, lets create a screenshot so we can see the page as it happened.
     * @param throwable the thrown exception that we are holding here
     * @param webDriver active WebDriver instance
     */
    public void onException(Throwable throwable, WebDriver webDriver) {
        try {
            if(TestConfigHelper.get().isTakeScreenshotOnError()) {
                File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
                String filename = new StringBuilder(UUID.randomUUID().toString())
                        .append("-FAILED-")
                        .append(throwable.getClass().getName())
                        .append(".jpeg")
                        .toString();

                FileUtils.copyFile(scrFile, new File(screenshotDirectory.getPath() + "/" + filename));
            }
        } catch (Exception e) {
            logger.error("Unable to Save to directory: {}", screenshotDirectory.getPath());
        }
    }
}