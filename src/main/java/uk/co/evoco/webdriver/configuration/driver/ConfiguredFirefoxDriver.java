package uk.co.evoco.webdriver.configuration.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import uk.co.evoco.webdriver.configuration.WebDriverConfig;

import java.io.File;
import java.io.IOException;

public class ConfiguredFirefoxDriver implements ConfiguredDriver {

    private WebDriverConfig webDriverConfig;
    private WebDriver webDriver;

    public ConfiguredFirefoxDriver(WebDriverConfig webDriverConfig) {
        this.webDriverConfig = webDriverConfig;
    }

    public void getRemoteDriver(FirefoxOptions firefoxOptions) {
        this.webDriver =  new RemoteWebDriver(this.webDriverConfig.getGridConfig().getGridUrl(), firefoxOptions);
    }

    public void getLocalDriver(FirefoxOptions firefoxOptions) throws IOException {
        createLogDirectory();
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"logs/firefox-driver.logs");
        WebDriverManager.firefoxdriver().setup();
        this.webDriver = new FirefoxDriver(firefoxOptions);
    }

    public FirefoxOptions getOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setHeadless(this.webDriverConfig.isHeadless());
        return firefoxOptions;
    }

    @Override
    public EventFiringWebDriver getDriver(File screenshotPath) throws IOException {
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
