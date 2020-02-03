package uk.co.evoco.webdriver.configuration.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import uk.co.evoco.webdriver.configuration.TestConfigManager;

public class ConfiguredSafariDriver implements ConfiguredDriver {

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
}
