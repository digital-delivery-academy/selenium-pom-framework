package uk.co.evoco.webdriver.configuration.driver;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConfiguredSafariDriverIT {

    private static final Logger logger = LoggerFactory.getLogger(ConfiguredSafariDriverIT.class);

    @Test
    public void testReturnsLocalWebDriver() {
        if(System.getProperty("os.name").contains("mac")) {
            ConfiguredSafariDriver configuredSafariDriver = new ConfiguredSafariDriver();
            WebDriver webDriver = configuredSafariDriver.getDriver(FileUtils.getTempDirectory());
            assertThat(webDriver, instanceOf(EventFiringWebDriver.class));
            webDriver.quit();
        } else {
            logger.warn("ConfiguredSafariDriverTests.testReturnsLocalWebDriver is " +
                    "dependant on being on Mac, you're not on Mac so it didn't run.");
        }
    }
}
