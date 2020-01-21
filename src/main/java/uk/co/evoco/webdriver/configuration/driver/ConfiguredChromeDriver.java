package uk.co.evoco.webdriver.configuration.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import uk.co.evoco.webdriver.configuration.WebDriverConfig;

import java.io.File;
import java.io.IOException;

public class ConfiguredChromeDriver implements ConfiguredDriver {

    private WebDriverConfig webDriverConfig;

    public ConfiguredChromeDriver(WebDriverConfig webDriverConfig) {
        this.webDriverConfig = webDriverConfig;
    }

    public WebDriver getRemoteDriver() {
        return new RemoteWebDriver(this.webDriverConfig.getGridConfig().getGridUrl(), this.getOptions());
    }

    public WebDriver getLocalDriver() throws IOException {
        createLogDirectory();
        System.setProperty("webdriver.chrome.logfile", "logs/chrome-driver.log");
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(this.getOptions());
    }

    private ChromeOptions getOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(this.webDriverConfig.isHeadless());
        return chromeOptions;
    }

    @Override
    public EventFiringWebDriver getDriver(File screenshotPath) throws IOException {
        WebDriver webDriver;
        switch(this.webDriverConfig.getRunType()) {
            case LOCAL:
                webDriver = getLocalDriver();
                break;
            case GRID:
                webDriver = getRemoteDriver();
                break;
            default:
                throw new RuntimeException("Must set runType to either LOCAL or GRID in configuration file");
        }
        return configureEventFiringWebDriver(
                webDriver,
                this.webDriverConfig.getWebDriverWaitTimeout(),
                screenshotPath);
    }
}
