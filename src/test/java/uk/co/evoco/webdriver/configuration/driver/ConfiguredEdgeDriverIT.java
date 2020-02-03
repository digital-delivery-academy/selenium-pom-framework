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

public class ConfiguredEdgeDriverIT {

    private static final Logger logger = LoggerFactory.getLogger(ConfiguredEdgeDriverIT.class);

    @Test
    public void testReturnsLocalWebDriver() throws IOException {
        if(System.getProperty("os.name").contains("win")) {
            ConfiguredEdgeDriver configuredEdgeDriver = new ConfiguredEdgeDriver();
            WebDriver webDriver = configuredEdgeDriver.getDriver(FileUtils.getTempDirectory());
            assertThat(webDriver, instanceOf(EventFiringWebDriver.class));
            webDriver.quit();
        } else {
            logger.warn("ConfiguredEdgeDriverTests.testReturnsLocalWebDriver is dependant on " +
                    "being on Windows, you're not on Windows so it didn't run.");
        }
    }
}
