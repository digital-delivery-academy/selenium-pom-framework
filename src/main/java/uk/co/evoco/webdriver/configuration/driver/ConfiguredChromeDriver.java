package uk.co.evoco.webdriver.configuration.driver;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.evoco.tests.BaseAbstractTest;
import uk.co.evoco.webdriver.configuration.BrowserType;
import uk.co.evoco.webdriver.configuration.TestConfigHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

public class ConfiguredChromeDriver implements ConfiguredDriver {

    private static final Logger logger = LoggerFactory.getLogger(ConfiguredChromeDriver.class);

    /**
     *
     *
     * @return WebDriver representing RemoteWebDriver grid
     */
    public WebDriver getRemoteDriver() throws IOException {
        ChromeOptions chromeOptions = this.getOptions();
        LoggingPreferences loggingOptions = this.getLoggerPrefs();
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("goog:loggingPrefs", loggingOptions);
        chromeOptions.merge(capabilities);
        return new RemoteWebDriver(
                TestConfigHelper.get().getGridConfig().getGridUrl(), chromeOptions);
    }

    /**
     *
     * @return WebDriver representing RemoteWebDriver grid
     * @throws IOException if log directory doesn't exist
     */
    public WebDriver getLocalDriver() throws IOException {
        ChromeOptions chromeOptions = this.getOptions();
        LoggingPreferences loggingOptions = this.getLoggerPrefs();
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("goog:loggingPrefs", loggingOptions);
        chromeOptions.merge(capabilities);

        createLogDirectory();
        System.setProperty("webdriver.chrome.logfile", "logs/chrome-driver.log");
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(chromeOptions);
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

        if(!getLoggerPrefs().getLevel(LogType.PERFORMANCE).getName().equals("OFF")) {
            Map<String, Object> perfLogPrefs = new HashMap<>();
            perfLogPrefs.put("traceCategories", "browser,devtools.timeline,devtools");
            chromeOptions.setExperimentalOption("perfLoggingPrefs", perfLogPrefs);
        }

        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        chromeOptions.setHeadless(TestConfigHelper.get().isHeadless());
        return chromeOptions;
    }

    public LoggingPreferences getLoggerPrefs() {
        LoggingPreferences loggingPreferences = new LoggingPreferences();
        TestConfigHelper.get()
                .getChromeLoggingPreferences()
                .ifPresent(logLevel -> {
                    try {
                        System.setProperty("webdriver.chrome.verboseLogging", "true");
                        loggingPreferences.enable(LogType.PERFORMANCE, parseLogLevel(logLevel));
                        loggingPreferences.enable(LogType.BROWSER, parseLogLevel(logLevel));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        return loggingPreferences;
    }

    private Level parseLogLevel(String logLevel) {
        try {
            return Level.parse(logLevel);
        }
        catch (IllegalArgumentException exception) {
            logger.warn("Incorrect Level provided so performance logging set to OFF");
            return Level.OFF;
        }
    }
}
