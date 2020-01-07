package uk.co.evoco.webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * This class uses the Builder Pattern to construct its options.  Calling .build() will
 * result in all of the configuration options being assembled and a valid WebDriver object being
 * supplied to the caller
 */
public class WebDriverBuilder {

    /**
     * The build method pulls all of the configuration options together and uses them to
     * construct an EventFiringWebDriver with the correct configuration for the browser type
     * @return
     */
    public EventFiringWebDriver build() {
        WebDriverManager.chromedriver().setup();
        WebDriver webDriver = new ChromeDriver();
        EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(webDriver);
        WebDriverListener eventListener = new WebDriverListener();
        eventFiringWebDriver.register(eventListener);

        return eventFiringWebDriver;
    }
}
