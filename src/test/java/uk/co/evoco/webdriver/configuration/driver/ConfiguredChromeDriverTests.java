package uk.co.evoco.webdriver.configuration.driver;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConfiguredChromeDriverTests {

    @Test
    public void testReturnsLocalWebDriver() throws IOException {
        ConfiguredDriver configuredChromeDriver = new ConfiguredChromeDriver();
        WebDriver webDriver = configuredChromeDriver.getDriver(FileUtils.getTempDirectory());
        assertThat(webDriver, instanceOf(EventFiringWebDriver.class));
    }

    @Test
    public void testGetOptionsReturnsOptionsIncludedInChromeConfig() {
        ConfiguredChromeDriver configuredChromeDriver = new ConfiguredChromeDriver();
        Map<String, Object> chromeOptions = getPreferences(configuredChromeDriver.getOptions());
        String expectedFileDownLoadPath = Paths.get("target").toAbsolutePath().toString() + "/" + "downloads/reports";
        assertThat(chromeOptions.get("profile.default_content_settings.popups"), is(0));
        assertThat(chromeOptions.get("download.default_directory"), is(expectedFileDownLoadPath));
        assertThat(chromeOptions.get("safebrowsing.enabled"), is(true));
    }

    private Map<String, Object> getPreferences(ChromeOptions options) {
        Map<String, Object> googleChromeOptions = (Map<String, Object>) options.asMap().get("goog:chromeOptions");
        return (Map<String, Object>) googleChromeOptions.get("prefs");
    }
}
