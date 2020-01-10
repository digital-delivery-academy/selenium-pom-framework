package uk.co.evoco.webdriver.configuration;

import uk.co.evoco.webdriver.utils.JsonUtils;

import java.io.IOException;

/**
 * Provides a way to load either a default configuration of a configuration from file
 */
public class ConfigurationLoader {

    private final String CONFIGURATION_FILE = "config.json";
    private boolean useDefaultConfig = false;
    private boolean useConfigFile = false;

    /**
     * Great for getting started quickly.  This will bypass the shipped "./src/text/resources/config.json" file
     * and will simply use CHROME.  There will be, by default though no base URL set to the configuration.
     * Returns reference to the class instance of WebDriverBuilder to maintain the builder pattern.
     * @return ConfigurationLoader
     */
    public ConfigurationLoader useDefaultConfiguration() {
        this.useDefaultConfig = true;
        return this;
    }

    /**
     * Loads a WebDriverConfiguration from the "./src/test/resources/config.json" file and configures a WebDriverConfig
     * object.
     * Returns reference to the class instance of WebDriverBuilder to maintain the builder pattern.
     * @return ConfigurationLoader
     * @throws IOException
     */
    public ConfigurationLoader useConfigurationFile() throws IOException {
        this.useConfigFile = true;
        return this;
    }

    /**
     * This method builds the configuration and returns it
     * @return WebDriverConfig
     * @throws IOException
     */
    public WebDriverConfig build() throws IOException {
        if(useDefaultConfig) {
            WebDriverConfig webDriverConfig;
            webDriverConfig = new WebDriverConfig();
            webDriverConfig.setBrowserType(BrowserType.CHROME);
            return webDriverConfig;
        } else if (useConfigFile) {
            return JsonUtils.fromFile(ClassLoader.getSystemResourceAsStream(CONFIGURATION_FILE), WebDriverConfig.class);
        } else {
            throw new RuntimeException("Must select either default configuration or configuration file");
        }
    }
}
