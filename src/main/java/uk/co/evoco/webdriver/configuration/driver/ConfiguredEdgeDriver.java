package uk.co.evoco.webdriver.configuration.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import uk.co.evoco.webdriver.configuration.TestConfigManager;

public class ConfiguredEdgeDriver implements ConfiguredDriver {

    /**
     *
     * @return WebDriver representing RemoteWebDriver grid
     */
    public WebDriver getRemoteDriver() {
        return new RemoteWebDriver(
                TestConfigManager.get().getGridConfig().getGridUrl(), this.getOptions());
    }

    /**
     *
     * @return WebDriver representing RemoteWebDriver grid
     */
    public WebDriver getLocalDriver() {
        WebDriverManager.iedriver().setup();
        return new EdgeDriver(this.getOptions());
    }

    /**
     *
     * @return configured options object for target browser driver
     */
    public EdgeOptions getOptions() {
        return new EdgeOptions();
    }
}
