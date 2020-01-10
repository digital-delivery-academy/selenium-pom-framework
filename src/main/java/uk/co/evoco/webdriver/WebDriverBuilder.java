package uk.co.evoco.webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import uk.co.evoco.webdriver.configuration.WebDriverConfig;

import java.io.File;

/**
 * This class uses the Builder Pattern to construct its options.  Calling .build() will
 * result in all of the configuration options being assembled and a valid WebDriver object being
 * supplied to the caller
 */
public class WebDriverBuilder {

    private WebDriverConfig webDriverConfig;
    private File screenshotDirectory;

    /**
     * Allows caller to provide a WebDriverConfig object.  Returns reference to the class instance of WebDriverBuilder
     * to maintain the builder pattern.
     * @param webDriverConfig
     * @return WebDriverBuilder
     */
    public WebDriverBuilder setConfiguration(WebDriverConfig webDriverConfig) {
        this.webDriverConfig = webDriverConfig;
        return this;
    }

    public WebDriverBuilder setResultsDirectory(File screenshotDirectory) {
        this.screenshotDirectory = screenshotDirectory;
        return this;
    }

    /**
     * The build method pulls all of the configuration options together and uses them to
     * construct an EventFiringWebDriver with the correct configuration for the browser type
     * @return EventFiringWebDriver
     */
    public EventFiringWebDriver build() {
        WebDriver webDriver;

        switch(this.webDriverConfig.getBrowserType()) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver();
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver();
                break;
            case IE:
                WebDriverManager.iedriver().setup();
                webDriver = new InternetExplorerDriver();
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                webDriver = new EdgeDriver();
                break;
            case SAFARI:
                webDriver = new SafariDriver();
                break;
            default:
                throw new RuntimeException("WebDriverBuilder has no valid target browser set in WebDriverConfig");
        }

        EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(webDriver);
        WebDriverListener eventListener = new WebDriverListener();
        eventListener.setWebdriverWaitTimeout(this.webDriverConfig.getWebDriverWaitTimeout());
        eventListener.setScreenshotDirectory(this.screenshotDirectory);
        eventFiringWebDriver.register(eventListener);

        return eventFiringWebDriver;
    }
}
