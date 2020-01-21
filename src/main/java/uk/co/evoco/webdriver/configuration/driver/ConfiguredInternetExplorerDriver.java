package uk.co.evoco.webdriver.configuration.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import uk.co.evoco.webdriver.configuration.WebDriverConfig;

import java.io.File;

public class ConfiguredInternetExplorerDriver implements ConfiguredDriver {

    private WebDriverConfig webDriverConfig;
    private WebDriver webDriver;

    public ConfiguredInternetExplorerDriver(WebDriverConfig webDriverConfig) {
        this.webDriverConfig = webDriverConfig;
    }

    public void getRemoteDriver(InternetExplorerOptions ieOptions) {
        this.webDriver =  new RemoteWebDriver(this.webDriverConfig.getGridConfig().getGridUrl(), ieOptions);
    }

    public void getLocalDriver(InternetExplorerOptions ieOptions) {
        WebDriverManager.iedriver().setup();
        this.webDriver = new InternetExplorerDriver(ieOptions);
    }

    public InternetExplorerOptions getOptions() {
        return new InternetExplorerOptions();
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
