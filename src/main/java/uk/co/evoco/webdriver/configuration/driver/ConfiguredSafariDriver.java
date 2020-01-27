package uk.co.evoco.webdriver.configuration.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import uk.co.evoco.webdriver.configuration.TestConfigManager;

import java.io.File;

public class ConfiguredSafariDriver implements ConfiguredDriver {

    /**
     *
     * @return WebDriver representing RemoteWebDriver grid
     */
    public WebDriver getRemoteDriver() {
        return new RemoteWebDriver(
                TestConfigManager.getInstance().getWebDriverConfig().getGridConfig().getGridUrl(), this.getOptions());
    }

    /**
     *
     * @return WebDriver representing RemoteWebDriver grid
     */
    public WebDriver getLocalDriver() {
        return new SafariDriver(this.getOptions());
    }

    /**
     *
     * @return configured options object for target browser driver
     */
    public SafariOptions getOptions() {
        return new SafariOptions();
    }

    /**
     * 
     * @param screenshotPath path to store screenshots
     * @return configured EventFiringWebDriver
     */
    @Override
    public EventFiringWebDriver getDriver(File screenshotPath) {
        WebDriver webDriver;
        switch(TestConfigManager.getInstance().getWebDriverConfig().getRunType()) {
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
                TestConfigManager.getInstance().getWebDriverConfig().getWebDriverWaitTimeout(),
                screenshotPath);
    }
}
