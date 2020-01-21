package uk.co.evoco.webdriver;

import org.openqa.selenium.support.events.EventFiringWebDriver;
import uk.co.evoco.webdriver.configuration.TestConfigManager;
import uk.co.evoco.webdriver.configuration.driver.*;

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
     * @param screenshotDirectory
     * @return
     */
    public WebDriverBuilder setResultsDirectory(File screenshotDirectory) {
        this.screenshotDirectory = screenshotDirectory;
        return this;
    }

    /**
     * The build method pulls all of the configuration options together and uses them to
     * construct an EventFiringWebDriver with the correct configuration for the browser type
     * @return EventFiringWebDriver
     */
    public EventFiringWebDriver build() throws IOException {
        switch(TestConfigManager.getInstance().getWebDriverConfig().getBrowserType()) {
            case CHROME:
                return new ConfiguredChromeDriver(TestConfigManager.getInstance().getWebDriverConfig()).getDriver(this.screenshotDirectory);
            case FIREFOX:
                return new ConfiguredFirefoxDriver(TestConfigManager.getInstance().getWebDriverConfig()).getDriver(this.screenshotDirectory);
            case IE:
                return new ConfiguredInternetExplorerDriver(TestConfigManager.getInstance().getWebDriverConfig()).getDriver(this.screenshotDirectory);
            case EDGE:
                return new ConfiguredEdgeDriver(TestConfigManager.getInstance().getWebDriverConfig()).getDriver(this.screenshotDirectory);
            case SAFARI:
                return new ConfiguredSafariDriver(TestConfigManager.getInstance().getWebDriverConfig()).getDriver(this.screenshotDirectory);
            default:
                throw new RuntimeException("WebDriverBuilder has no valid target browser set in WebDriverConfig");
        }
    }
}
