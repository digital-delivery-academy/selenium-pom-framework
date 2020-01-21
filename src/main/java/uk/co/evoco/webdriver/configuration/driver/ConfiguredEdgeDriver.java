package uk.co.evoco.webdriver.configuration.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import uk.co.evoco.webdriver.configuration.WebDriverConfig;

import java.io.File;

public class ConfiguredEdgeDriver implements ConfiguredDriver {

    private WebDriverConfig webDriverConfig;
    private WebDriver webDriver;

    public ConfiguredEdgeDriver(WebDriverConfig webDriverConfig) {
        this.webDriverConfig = webDriverConfig;
    }

    public void getRemoteDriver(EdgeOptions edgeOptions) {
        this.webDriver =  new RemoteWebDriver(this.webDriverConfig.getGridConfig().getGridUrl(), edgeOptions);
    }

    public void getLocalDriver(EdgeOptions edgeOptions) {
        WebDriverManager.iedriver().setup();
        this.webDriver = new EdgeDriver(edgeOptions);
    }

    public EdgeOptions getOptions() {
        return new EdgeOptions();
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
