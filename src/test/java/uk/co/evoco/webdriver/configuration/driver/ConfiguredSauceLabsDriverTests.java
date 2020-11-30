package uk.co.evoco.webdriver.configuration.driver;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import uk.co.evoco.exceptions.SauceLabsCredentialsException;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class ConfiguredSauceLabsDriverTests {

    @Test
    public void testCanRunTestOnSauceLabsUsingConfigiration() throws SauceLabsCredentialsException, IOException {
        System.setProperty("config", "config-saucelabs.json");
        ConfiguredDriver configuredSauceLabsGridDriver = new ConfiguredSauceLabsGridDriver();
        WebDriver webDriver = configuredSauceLabsGridDriver.getDriver(FileUtils.getTempDirectory());
        webDriver.get("https://www.evoco.co.uk");
        assertThat("Didn't make it to evoco.co.uk", webDriver.getCurrentUrl(), containsString("evoco.co.uk"));
        webDriver.quit();
    }
}
