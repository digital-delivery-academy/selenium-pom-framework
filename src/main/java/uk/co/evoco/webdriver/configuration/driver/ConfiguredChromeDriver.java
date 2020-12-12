package uk.co.evoco.webdriver.configuration.driver;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import uk.co.evoco.webdriver.configuration.BrowserType;
import uk.co.evoco.webdriver.configuration.TestConfigHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConfiguredChromeDriver implements ConfiguredDriver {

    /**
     *
     *
     * @return WebDriver representing RemoteWebDriver grid
     */
    public WebDriver getRemoteDriver() throws IOException {
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
    public ChromeOptions getOptions() throws IOException {
        ChromeOptions chromeOptions = new ChromeOptions();
        Map<String, Object> chromePrefs = new HashMap<>();
        Iterator<Map.Entry<String, JsonNode>> browserPreferences = TestConfigHelper.get()
                .getBrowserPreferences(BrowserType.CHROME)
                .fields();
        while (browserPreferences.hasNext()) {
            Map.Entry<String, JsonNode> entry = browserPreferences.next();
            JsonNode value = entry.getValue();
            String key = entry.getKey();
            switch (value.getNodeType()) {
                case BOOLEAN:
                    chromePrefs.put(key, value.asBoolean());
                    break;
                case NUMBER:
                    chromePrefs.put(key, value.asInt());
                    break;
                default:
                    if (key.equals("download.default_directory")) {
                        chromePrefs.put(key, createFileDownloadDirectory(value.asText()));
                    } else {
                        chromePrefs.put(key, value.asText());
                    }
            }
        }
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        chromeOptions.setHeadless(TestConfigHelper.get().isHeadless());
        return chromeOptions;
    }
}
