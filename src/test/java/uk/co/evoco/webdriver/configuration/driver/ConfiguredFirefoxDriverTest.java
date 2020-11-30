package uk.co.evoco.webdriver.configuration.driver;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConfiguredFirefoxDriverTest {

    @Test
    public void testReturnsLocalWebDriver() throws IOException {
        System.setProperty("config", "DEFAULT");
        ConfiguredDriver configuredFirefoxDriver = new ConfiguredFirefoxDriver();
        WebDriver webDriver = configuredFirefoxDriver.getDriver(FileUtils.getTempDirectory());
        assertThat(webDriver, instanceOf(EventFiringWebDriver.class));
        webDriver.quit();
    }

    @Test
    public void testGetOptionsReturnsOptionsIncludedInFireFoxConfig() {
        ConfiguredDriver configuredFirefoxDriver = new ConfiguredFirefoxDriver();
        Map<String, Object> firefoxPreferences = getPreferences(configuredFirefoxDriver.getOptions());
        assertThat(firefoxPreferences.get("pdfjs.disabled"), is(true));
        assertThat(firefoxPreferences.get("browser.download.folderList"), is(2));
        assertThat(firefoxPreferences.get("browser.download.defaultFolder"), is("downloads/reports"));
    }

    private Map<String, Object> getPreferences (FirefoxOptions options) {
        Map<String, Object> moxOptions = (Map<String, Object>)options.asMap().get("moz:firefoxOptions");
        return (Map<String, Object>)moxOptions.get("prefs");
    }
}
