package uk.co.evoco.webdriver.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConfigurationLoaderTests {

    private ConfigurationLoader configurationLoader;

    @BeforeEach
    public void setUp() {
        this.configurationLoader = new ConfigurationLoader();
    }

    @Test
    public void testCanLoadConfigurationFileWithNoPropertyArgumentsSet() throws IOException {
        WebDriverConfig webDriverConfig = configurationLoader
                .decideWhichConfigurationToUse()
                .build();
        assertThat(webDriverConfig.getBaseUrl(), is("https://www.google.com"));
    }

    @Test
    public void testCanLoadConfigurationFileWithConfigurationPropertySetToDefault() throws IOException {
        System.setProperty("config", "DEFAULT");
        WebDriverConfig webDriverConfig = configurationLoader
                .decideWhichConfigurationToUse()
                .build();
        assertThat(webDriverConfig.getBaseUrl(), is("https://www.google.com"));
    }

    @Test
    public void testCanLoadConfigurationFileWithConfigurationPropertySetToExternalFile() throws IOException {
        System.setProperty("config", "./config-fs.json");
        WebDriverConfig webDriverConfig = configurationLoader
                .decideWhichConfigurationToUse()
                .build();
        assertThat(webDriverConfig.getBaseUrl(), is("https://www.yahoo.com"));
    }
}
