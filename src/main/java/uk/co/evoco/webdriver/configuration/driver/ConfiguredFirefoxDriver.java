package uk.co.evoco.webdriver.configuration.driver;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import uk.co.evoco.webdriver.configuration.BrowserType;
import uk.co.evoco.webdriver.configuration.TestConfigManager;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class ConfiguredFirefoxDriver implements ConfiguredDriver {

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
     * @throws IOException if log directory doesn't exist
     */
    public WebDriver getLocalDriver() throws IOException {
        createLogDirectory();
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"logs/firefox-driver.logs");
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(this.getOptions());
    }

    /**
     *
     * @return configured options object for target browser driver
     */
    public FirefoxOptions getOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        Iterator<Map.Entry<String, JsonNode>> firefoxPreferences = TestConfigManager.get()
                .getBrowserPreferences(BrowserType.FIREFOX)
                .fields();

        while (firefoxPreferences.hasNext()) {
            Map.Entry<String, JsonNode> entry = firefoxPreferences.next();
            JsonNode value = entry.getValue();
            String key = entry.getKey();
            switch (value.getNodeType()) {
                case BOOLEAN:
                    firefoxOptions.addPreference(key, value.asBoolean());
                    break;
                case NUMBER:
                    firefoxOptions.addPreference(key, value.asInt());
                    break;
                default:
                    firefoxOptions.addPreference(key, value.asText());
            }
        }

        firefoxOptions.setHeadless(TestConfigManager.get().isHeadless());
        return firefoxOptions;
    }
}
