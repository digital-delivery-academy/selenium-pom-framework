package uk.co.evoco.webdriver.configuration.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import uk.co.evoco.webdriver.configuration.TestConfigManager;

import java.io.IOException;

public class ConfiguredFirefoxDriver implements ConfiguredDriver {

    /**
     *
     * @return WebDriver representing RemoteWebDriver grid
     */
    public WebDriver getRemoteDriver() {
        return new RemoteWebDriver(
                TestConfigManager.get().getGridConfig().getGridUrl(), this.getOptions());
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
        firefoxOptions.setHeadless(TestConfigManager.get().isHeadless());
        return firefoxOptions;
    }
}
