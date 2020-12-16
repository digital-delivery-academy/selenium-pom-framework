package uk.co.evoco.webdriver.configuration.driver;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConfiguredFirefoxDriverTests {

    @Test
    public void testReturnsLocalWebDriver() throws IOException {
        System.setProperty("config", "DEFAULT");
        ConfiguredDriver configuredFirefoxDriver = new ConfiguredFirefoxDriver();
        WebDriver webDriver = configuredFirefoxDriver.getDriver(FileUtils.getTempDirectory());
        assertThat(webDriver, instanceOf(EventFiringWebDriver.class));
        webDriver.quit();
    }

    @Test
    public void testGetOptionsReturnsOptionsIncludedInFireFoxConfig() throws IOException {
        ConfiguredDriver configuredFirefoxDriver = new ConfiguredFirefoxDriver();
        Map<String, Object> firefoxPreferences = getPreferences(configuredFirefoxDriver.getOptions());
        String expectedFileDownLoadPath = new File("run-generated-files/firefox/downloads").getCanonicalPath();
        assertThat(firefoxPreferences.get("browser.download.folderList"), is(2));
        assertThat(firefoxPreferences.get("browser.helperApps.alwaysAsk.force"), is(false));
        assertThat(firefoxPreferences.get("browser.download.manager.showWhenStarting"), is(false));
        assertThat(firefoxPreferences.get("browser.helperApps.neverAsk.saveToDisk"), is("application/pdf, application/octet-stream"));
        assertThat(firefoxPreferences.get("pdfjs.disabled"), is(true));
        assertThat(firefoxPreferences.get("unhandled_prompt_behaviour"), is(false));
        assertThat(firefoxPreferences.get("browser.download.dir"), is(expectedFileDownLoadPath));
    }

    private Map<String, Object> getPreferences(FirefoxOptions options) {
        Map<String, Object> moxOptions = (Map<String, Object>) options.asMap().get("moz:firefoxOptions");
        return (Map<String, Object>) moxOptions.get("prefs");
    }
}
