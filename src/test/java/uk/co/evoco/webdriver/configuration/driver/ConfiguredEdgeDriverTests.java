package uk.co.evoco.webdriver.configuration.driver;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConfiguredEdgeDriverTests {

    private static final Logger logger = LogManager.getLogger(ConfiguredEdgeDriverTests.class);

    @Test
    public void testReturnsLocalWebDriver() throws IOException {
        if(System.getProperty("os.name").contains("win")) {
            ConfiguredDriver configuredEdgeDriver = new ConfiguredEdgeDriver();
            WebDriver webDriver = configuredEdgeDriver.getDriver(FileUtils.getTempDirectory());
            assertThat(webDriver, instanceOf(EventFiringWebDriver.class));
            webDriver.quit();
        } else {
            logger.warn("ConfiguredEdgeDriverTests.testReturnsLocalWebDriver is dependant on " +
                    "being on Windows, you're not on Windows so it didn't run.");
        }
    }
}
