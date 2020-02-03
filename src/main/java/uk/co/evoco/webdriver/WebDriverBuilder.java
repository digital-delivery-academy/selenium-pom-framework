package uk.co.evoco.webdriver;

import org.openqa.selenium.support.events.EventFiringWebDriver;
import uk.co.evoco.webdriver.configuration.TestConfigManager;
import uk.co.evoco.webdriver.configuration.driver.ConfiguredChromeDriver;
import uk.co.evoco.webdriver.configuration.driver.ConfiguredFirefoxDriver;
import uk.co.evoco.webdriver.configuration.driver.ConfiguredEdgeDriver;
import uk.co.evoco.webdriver.configuration.driver.ConfiguredInternetExplorerDriver;
import uk.co.evoco.webdriver.configuration.driver.ConfiguredSafariDriver;

import java.io.File;
import java.io.IOException;

/**
 * This class uses the Builder Pattern to construct its options.  Calling .build() will
 * result in all of the configuration options being assembled and a valid WebDriver object being
 * supplied to the caller
 */
public class WebDriverBuilder {
    
    private File screenshotDirectory;
    
    /**
     * 
     * @param screenshotDirectory path for the directory storing screenshots
     * @return WebDriverBuilder as using builder pattern
     */
    public WebDriverBuilder setResultsDirectory(File screenshotDirectory) {
        this.screenshotDirectory = screenshotDirectory;
        return this;
    }

    /**
     * The build method pulls all of the configuration options together and uses them to
     * construct an EventFiringWebDriver with the correct configuration for the browser type
     * @return EventFiringWebDriver representing the configured driver
     * @throws IOException if log file for browser driver logs cannot be created
     */
    public EventFiringWebDriver build() throws IOException {
        switch(TestConfigManager.get().getBrowserType()) {
            case CHROME:
                return new ConfiguredChromeDriver().getDriver(this.screenshotDirectory);
            case FIREFOX:
                return new ConfiguredFirefoxDriver().getDriver(this.screenshotDirectory);
            case IE:
                return new ConfiguredInternetExplorerDriver().getDriver(this.screenshotDirectory);
            case EDGE:
                return new ConfiguredEdgeDriver().getDriver(this.screenshotDirectory);
            case SAFARI:
                return new ConfiguredSafariDriver().getDriver(this.screenshotDirectory);
            default:
                throw new RuntimeException("WebDriverBuilder has no valid target browser set in WebDriverConfig");
        }
    }
}
