package uk.co.evoco.webdriver.configuration.driver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import uk.co.evoco.webdriver.WebDriverListener;
import uk.co.evoco.webdriver.configuration.TestConfigManager;

import java.io.File;
import java.io.IOException;

public interface ConfiguredDriver {

     WebDriver getLocalDriver() throws IOException;
     WebDriver getRemoteDriver();

    /**
     *ß
     * @param screenshotPath path to store screenshots
     * @return configured EventFiringWebDriver
     * @throws IOException if log directory doesn't exist
     */
    default EventFiringWebDriver getDriver(File screenshotPath) throws IOException {
        WebDriver webDriver;
        switch(TestConfigManager.get().getRunType()) {
            case LOCAL:
                webDriver = getLocalDriver();
                break;
            case GRID:
                webDriver = getRemoteDriver();
                break;
            default:
                throw new WebDriverException("Must set runType to either LOCAL or GRID in configuration file");
        }
        return configureEventFiringWebDriver(
                webDriver,
                TestConfigManager.get().getWebDriverWaitTimeout(),
                screenshotPath);
    }

    /**
     *
     * @param webDriver active WebDriver instance
     * @param timeout timeout for WebDriverWaits
     * @param screenshotDirectory path to store screenshots
     * @return configured options object for target browser driver
     */
    default EventFiringWebDriver configureEventFiringWebDriver(
            WebDriver webDriver, long timeout, File screenshotDirectory) {
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
