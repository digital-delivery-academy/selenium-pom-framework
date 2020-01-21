package uk.co.evoco.webdriver.configuration.driver;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import uk.co.evoco.webdriver.configuration.WebDriverConfig;
import uk.co.evoco.webdriver.utils.JsonUtils;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConfiguredChromeDriverIT {

    @Test
    public void testReturnsLocalWebDriver() throws IOException {
        WebDriverConfig webDriverConfig = JsonUtils.fromFile(ClassLoader.getSystemResourceAsStream("fixtures/sample-config.json"), WebDriverConfig.class);
        ConfiguredChromeDriver configuredChromeDriver = new ConfiguredChromeDriver(webDriverConfig);
        WebDriver webDriver = configuredChromeDriver.getDriver(FileUtils.getTempDirectory());
        assertThat(webDriver, instanceOf(EventFiringWebDriver.class));
    }
}