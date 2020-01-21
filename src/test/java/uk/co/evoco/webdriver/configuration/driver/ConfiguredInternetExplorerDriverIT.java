package uk.co.evoco.webdriver.configuration.driver;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.evoco.webdriver.configuration.WebDriverConfig;
import uk.co.evoco.webdriver.utils.JsonUtils;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConfiguredInternetExplorerDriverIT {

    private static final Logger logger = LoggerFactory.getLogger(ConfiguredInternetExplorerDriverIT.class);

    @Test
    public void testReturnsLocalWebDriver() throws IOException {
        if(System.getProperty("os.name").contains("win")) {
            WebDriverConfig webDriverConfig = JsonUtils.fromFile(ClassLoader.getSystemResourceAsStream("fixtures/sample-config.json"), WebDriverConfig.class);
            ConfiguredInternetExplorerDriver configuredIeDriver = new ConfiguredInternetExplorerDriver(webDriverConfig);
            WebDriver webDriver = configuredIeDriver.getDriver(FileUtils.getTempDirectory());
            assertThat(webDriver, instanceOf(EventFiringWebDriver.class));
            webDriver.quit();
        } else {
            logger.warn("ConfiguredInternetExplorerTests.testReturnsLocalWebDriver is dependant on being on Windows, you're not on Windows so it didn't run.");
        }
    }
}
