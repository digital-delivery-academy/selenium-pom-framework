package uk.co.evoco.webdriver.configuration.driver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import uk.co.evoco.webdriver.WebDriverListener;
import uk.co.evoco.webdriver.configuration.TestConfigHelper;

import java.io.File;
import java.io.IOException;

public interface ConfiguredDriver {

     WebDriver getLocalDriver() throws IOException;
     WebDriver getRemoteDriver();
     <T> T getOptions();

    /**
     *ß
     * @param screenshotPath path to store screenshots
     * @return configured EventFiringWebDriver
     * @throws IOException if log directory doesn't exist
     */
    default EventFiringWebDriver getDriver(File screenshotPath) throws IOException {
        WebDriver webDriver;
        switch(TestConfigHelper.get().getRunType()) {
            case LOCAL:
                webDriver = getLocalDriver();
                break;
            case GRID:
            case SAUCELABS:
                webDriver = getRemoteDriver();
                break;
            default:
                throw new WebDriverException("Must set runType to either LOCAL or GRID in configuration file");
        }
        return configureEventFiringWebDriver(webDriver, screenshotPath);
    }

    /**
     *
     * @param webDriver active WebDriver instance
     * @param screenshotDirectory path to store screenshots
     * @return configured options object for target browser driver
     */
    default EventFiringWebDriver configureEventFiringWebDriver(
            WebDriver webDriver, File screenshotDirectory) {
        EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(webDriver);
        WebDriverListener eventListener = new WebDriverListener();
        eventListener.setScreenshotDirectory(screenshotDirectory);
        eventFiringWebDriver.register(eventListener);

        return eventFiringWebDriver;
    }

    /**
     *
     * @throws IOException if the log directory cannot be created
     */
    default void createLogDirectory() throws IOException {
        FileUtils.forceMkdir(new File("./logs"));
    }
}
