package uk.co.evoco.webdriver.configuration.driver;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.powermock.api.mockito.PowerMockito;
import uk.co.evoco.webdriver.configuration.TestConfigHelper;
import uk.co.evoco.webdriver.configuration.WebDriverConfig;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConfiguredChromeDriverTests {

    @Mock
    WebDriverConfig webDriverConfigMock = mock(WebDriverConfig.class);

    @Test
    public void testReturnsLocalWebDriver() throws IOException, IllegalAccessException {
        Field testConfigHelperStaticVariable = PowerMockito.field(TestConfigHelper.class, "testConfigHelper");
        testConfigHelperStaticVariable.set(TestConfigHelper.class, null);

        ConfiguredDriver configuredChromeDriver = new ConfiguredChromeDriver();
        WebDriver webDriver = configuredChromeDriver.getDriver(FileUtils.getTempDirectory());
        assertThat(webDriver, instanceOf(EventFiringWebDriver.class));
    }

    @Test
    public void testGetOptionsReturnsOptionsIncludedInChromeConfig() throws IOException, IllegalAccessException {
        Field testConfigHelperStaticVariable = PowerMockito.field(TestConfigHelper.class, "testConfigHelper");
        testConfigHelperStaticVariable.set(TestConfigHelper.class, null);

        ConfiguredChromeDriver configuredChromeDriver = new ConfiguredChromeDriver();
        Map<String, Object> chromeOptions = getOptions(configuredChromeDriver.getOptions());
        String expectedFileDownLoadPath = new File("run-generated-files/chrome/downloads").getCanonicalPath();
        assertThat(chromeOptions.get("profile.default_content_settings.popups"), is(0));
        assertThat(chromeOptions.get("download.default_directory"), is(expectedFileDownLoadPath));
        assertThat(chromeOptions.get("safebrowsing.enabled"), is(true));
    }

    @Test
    public void testGetLoggingPreferencesReturnsEnabledLogLevel() throws Exception {

        Optional<String> mockConfigReturn = Optional.of("FINE");
        mockTestConfig();
        when(webDriverConfigMock.getChromeLoggingPreferences()).thenReturn(mockConfigReturn);


        ConfiguredChromeDriver configuredChromeDriver = new ConfiguredChromeDriver();
        LoggingPreferences loggingPreferences = configuredChromeDriver.getLoggerPrefs();

        Level googleChromeLoggingPreferences = loggingPreferences.getLevel("performance");

        assertThat(googleChromeLoggingPreferences, is(Level.FINE));
    }

    @Test
    public void testEmptyLoggingPreferencesReturnsOffLevel() throws IllegalAccessException {
        Optional<String> mockConfigReturn = Optional.of("");
        mockTestConfig();
        when(webDriverConfigMock.getChromeLoggingPreferences()).thenReturn(mockConfigReturn);

        ConfiguredChromeDriver configuredChromeDriver = new ConfiguredChromeDriver();
        LoggingPreferences loggingPreferences = configuredChromeDriver.getLoggerPrefs();

        Level googleChromeLoggingPreferences = loggingPreferences.getLevel("performance");

        assertThat(googleChromeLoggingPreferences, is(Level.OFF));
    }

    private Map<String, Object> getOptions(ChromeOptions options) {
        Map<String, Object> googleChromeOptions = (Map<String, Object>) options.asMap().get("goog:chromeOptions");
        return (Map<String, Object>) googleChromeOptions.get("prefs");
    }

    private void mockTestConfig() throws IllegalAccessException {
        Field testConfigHelperStaticVariable = PowerMockito.field(TestConfigHelper.class, "testConfigHelper");
        testConfigHelperStaticVariable.set(TestConfigHelper.class, mock(TestConfigHelper.class));
        Field webdriverConfigStaticVariable = PowerMockito.field(TestConfigHelper.class, "webDriverConfig");
        webdriverConfigStaticVariable.set(TestConfigHelper.class, webDriverConfigMock);
    }
}
