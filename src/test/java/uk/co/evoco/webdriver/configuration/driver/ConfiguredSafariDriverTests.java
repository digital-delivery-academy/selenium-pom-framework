package uk.co.evoco.webdriver.configuration.driver;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConfiguredSafariDriverTests {

    private static final Logger logger = LoggerFactory.getLogger(ConfiguredSafariDriverTests.class);

    @Test
    public void testReturnsLocalWebDriver() throws IOException {
        if(System.getProperty("os.name").contains("mac")) {
            ConfiguredDriver configuredSafariDriver = new ConfiguredSafariDriver();
            WebDriver webDriver = configuredSafariDriver.getDriver(FileUtils.getTempDirectory());
            assertThat(webDriver, instanceOf(EventFiringWebDriver.class));
            webDriver.quit();
        } else {
            logger.warn("ConfiguredSafariDriverTests.testReturnsLocalWebDriver is " +
                    "dependant on being on Mac, you're not on Mac so it didn't run.");
        }
    }
}
