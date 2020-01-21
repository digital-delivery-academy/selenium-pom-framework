package uk.co.evoco.webdriver.configuration.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import uk.co.evoco.webdriver.configuration.WebDriverConfig;

import java.io.File;

public class ConfiguredSafariDriver implements ConfiguredDriver {

    private WebDriverConfig webDriverConfig;
    private WebDriver webDriver;

    public ConfiguredSafariDriver(WebDriverConfig webDriverConfig) {
        this.webDriverConfig = webDriverConfig;
    }

    public void getRemoteDriver(SafariOptions safariOptions) {
        this.webDriver =  new RemoteWebDriver(this.webDriverConfig.getGridConfig().getGridUrl(), safariOptions);
    }

    public void getLocalDriver(SafariOptions safariOptions) {
        this.webDriver = new SafariDriver(safariOptions);
    }

    public SafariOptions getOptions() {
        return new SafariOptions();
    }

    @Override
    public EventFiringWebDriver getDriver(File screenshotPath) {
        switch(this.webDriverConfig.getRunType()) {
            case LOCAL:
                getLocalDriver(this.getOptions());
                break;
            case GRID:
                getRemoteDriver(this.getOptions());
                break;
            default:
                throw new RuntimeException("Must set runType to either LOCAL or GRID in configuration file");
        }
        return configureEventFiringWebDriver(
                this.webDriver,
                this.webDriverConfig.getWebDriverWaitTimeout(),
                screenshotPath);
    }
}
