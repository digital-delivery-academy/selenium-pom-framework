package uk.co.evoco.webdriver.configuration.driver;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConfiguredFirefoxDriverIT {

    @Test
    public void testReturnsLocalWebDriver() throws IOException {
        ConfiguredFirefoxDriver configuredFirefoxDriver = new ConfiguredFirefoxDriver();
        WebDriver webDriver = configuredFirefoxDriver.getDriver(FileUtils.getTempDirectory());
        assertThat(webDriver, instanceOf(EventFiringWebDriver.class));
    }
}