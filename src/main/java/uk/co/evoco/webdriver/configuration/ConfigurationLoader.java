package uk.co.evoco.webdriver.configuration;

import uk.co.evoco.webdriver.configuration.utils.FileLoader;
import uk.co.evoco.webdriver.utils.JsonUtils;

import java.io.IOException;

/**
 * Provides a way to load either a default configuration of a configuration from file
 */
public class ConfigurationLoader {

    private final String INTERNAL_CONFIGURATION_FILE = "config.json";
    private String targetConfigurationFile;
    private boolean useDefaultConfig = false;
    private boolean useConfigFile = false;

    /**
     * Method for figuring out if we're using the internal, default configuration, or we're setting a reference
     * to an external configuration file (from the file system).
     * @return ConfigurationLoader
     */
    public ConfigurationLoader decideWhichConfigurationToUse() {
        String configurationProperty = System.getProperty("config", "DEFAULT");
        if(configurationProperty.toUpperCase().trim().equals("DEFAULT")) {
            useDefaultConfig = true;
            this.targetConfigurationFile = INTERNAL_CONFIGURATION_FILE;
            return this;
        }

        if(configurationProperty.toLowerCase().trim().contains(".json")) {
            useConfigFile = true;
            this.targetConfigurationFile = configurationProperty;
            return this;
        }

        throw new RuntimeException("Issue figuring out what configuration to use");
    }

    /**
     * This method builds the configuration and returns it
     * @return WebDriverConfig
     * @throws IOException
     */
    public WebDriverConfig build() throws IOException {
        return JsonUtils.fromFile(FileLoader.loadFromClasspathOrFileSystem(targetConfigurationFile), WebDriverConfig.class);
    }
}