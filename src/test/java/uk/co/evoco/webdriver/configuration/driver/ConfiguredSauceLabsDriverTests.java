package uk.co.evoco.webdriver.configuration.driver;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import uk.co.evoco.exceptions.SauceLabsCredentialsException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class ConfiguredSauceLabsDriverTests {

    @Test
    public void testCanRunTestOnSauceLabsUsingConfigiration() throws SauceLabsCredentialsException{
        System.setProperty("config", "config-saucelabs.json");
        ConfiguredDriver configuredSauceLabsGridDriver = new ConfiguredSauceLabsGridDriver();
        WebDriver webDriver = configuredSauceLabsGridDriver.getRemoteDriver();
        webDriver.get("https://www.evoco.co.uk");
        assertThat("Didn't make it to evoco.co.uk", webDriver.getCurrentUrl(), containsString("evoco.co.uk"));
        webDriver.quit();
    }
}
