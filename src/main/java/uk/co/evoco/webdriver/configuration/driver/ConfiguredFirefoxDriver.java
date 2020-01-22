package uk.co.evoco.webdriver.configuration.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import uk.co.evoco.webdriver.configuration.TestConfigManager;
import uk.co.evoco.webdriver.configuration.WebDriverConfig;

import java.io.File;
import java.io.IOException;

public class ConfiguredFirefoxDriver implements ConfiguredDriver {

    /**
     *
     * @return WebDriver representing RemoteWebDriver grid
     */
    public WebDriver getRemoteDriver() {
        return new RemoteWebDriver(TestConfigManager.getInstance().getWebDriverConfig().getGridConfig().getGridUrl(), this.getOptions());
    }

    /**
     *
     * @return WebDriver representing RemoteWebDriver grid
     * @throws IOException if log directory doesn't exist
     */
    public WebDriver getLocalDriver() throws IOException {
        createLogDirectory();
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"logs/firefox-driver.logs");
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(this.getOptions());
    }

    /**
     *
     * @return configured options object for target browser driver
     */
    public FirefoxOptions getOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setHeadless(TestConfigManager.getInstance().getWebDriverConfig().isHeadless());
        return firefoxOptions;
    }

    /**
     *
     * @param screenshotPath path to store screenshots
     * @return configured EventFiringWebDriver
     * @throws IOException if log directory doesn't exist
     */
    @Override
    public EventFiringWebDriver getDriver(File screenshotPath) throws IOException {
        WebDriver webDriver;
        switch(TestConfigManager.getInstance().getWebDriverConfig().getRunType()) {
            case LOCAL:
                webDriver = getLocalDriver();
                break;
            case GRID:
                webDriver =getRemoteDriver();
                break;
            default:
                throw new RuntimeException("Must set runType to either LOCAL or GRID in configuration file");
        }
        return configureEventFiringWebDriver(
                webDriver,
                TestConfigManager.getInstance().getWebDriverConfig().getWebDriverWaitTimeout(),
                screenshotPath);
    }
}
