package uk.co.evoco.webdriver.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConfigurationLoaderTests {

    private ConfigurationLoader configurationLoader;

    @BeforeEach
    public void setUp() {
        this.configurationLoader = new ConfigurationLoader();
    }

    @Test
    public void testCanLoadConfigurationFileWithNoPropertyArgumentsSet() {
        WebDriverConfig webDriverConfig = configurationLoader
                .decideWhichConfigurationToUse()
                .build();
        assertThat(webDriverConfig.getBaseUrl(), is("https://www.google.com"));
    }

    @Test
    public void testCanLoadConfigurationFileWithConfigurationPropertySetToDefault() {
        System.setProperty("config", "DEFAULT");
        WebDriverConfig webDriverConfig = configurationLoader
                .decideWhichConfigurationToUse()
                .build();
        assertThat(webDriverConfig.getBaseUrl(), is("https://www.google.com"));
    }

    @Test
    public void testCanLoadConfigurationFileWithConfigurationPropertySetToExternalFile() {
        System.setProperty("config", "./config-fs.json");
        WebDriverConfig webDriverConfig = configurationLoader
                .decideWhichConfigurationToUse()
                .build();
        assertThat(webDriverConfig.getBaseUrl(), is("https://www.yahoo.com"));
    }
}
