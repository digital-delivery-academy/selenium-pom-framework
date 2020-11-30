package uk.co.evoco.webdriver.configuration;

import com.saucelabs.saucebindings.Browser;
import org.junit.jupiter.api.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SauceLabsConfigurationLoaderTests {

    @Test
    public void testCanLoadConfigurationFromClasspath() {
        SauceLabsConfig sauceLabsConfig = new SauceLabsConfigurationLoader().load("saucelabs.json");
        assertThat(sauceLabsConfig.getBrowserName(), is(Browser.CHROME));
    }

    @Test
    public void testCanLoadConfigurationFromLocalFileSystem() {
        SauceLabsConfig sauceLabsConfig = new SauceLabsConfigurationLoader().load("saucelabs-fs.json");
        assertThat(sauceLabsConfig.getBrowserName(), is(Browser.CHROME));
    }

    @Test
    public void testCannotLoadConfigurationFromFile() {
        assertThrows(RuntimeException.class, () -> {
            new SauceLabsConfigurationLoader().load("file-does-not-exist.json");
        });
    }
}
