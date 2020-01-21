package uk.co.evoco.webdriver.configuration.driver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import uk.co.evoco.webdriver.WebDriverListener;

import java.io.File;
import java.io.IOException;

public interface ConfiguredDriver {

    EventFiringWebDriver getDriver(File screenshotPath) throws IOException;

    default EventFiringWebDriver configureEventFiringWebDriver(WebDriver webDriver, long timeout, File screenshotDirectory) {
        EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(webDriver);
        WebDriverListener eventListener = new WebDriverListener();
        eventListener.setWebdriverWaitTimeout(timeout);
        eventListener.setScreenshotDirectory(screenshotDirectory);
        eventFiringWebDriver.register(eventListener);

        return eventFiringWebDriver;
    }

    default void createLogDirectory() throws IOException {
        FileUtils.forceMkdir(new File("./logs"));
    }
}
