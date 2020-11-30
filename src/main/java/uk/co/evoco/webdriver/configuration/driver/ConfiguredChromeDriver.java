package uk.co.evoco.webdriver.configuration.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import uk.co.evoco.webdriver.configuration.TestConfigHelper;

import java.io.IOException;

public class ConfiguredChromeDriver implements ConfiguredDriver {

    /**
     *
     * @return WebDriver representing RemoteWebDriver grid
     */
    public WebDriver getRemoteDriver() {
        return new RemoteWebDriver(
                TestConfigHelper.get().getGridConfig().getGridUrl(), this.getOptions());
    }

    /**
     *
     * @return WebDriver representing RemoteWebDriver grid
     * @throws IOException if log directory doesn't exist
     */
    public WebDriver getLocalDriver() throws IOException {
        createLogDirectory();
        System.setProperty("webdriver.chrome.logfile", "logs/chrome-driver.log");
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(this.getOptions());
    }

    /**
     *
     * @return configured options object for target browser driver
     */
    public ChromeOptions getOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(TestConfigHelper.get().isHeadless());
        return chromeOptions;
    }
}
