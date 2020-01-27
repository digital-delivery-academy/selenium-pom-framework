package uk.co.evoco.webdriver.configuration;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class TestConfigManagerTests {

    @Test
    public void testCanAccessTestConfigurationIsCorrectType() {
        assertThat(TestConfigManager.getInstance().getWebDriverConfig(), instanceOf(WebDriverConfig.class));
    }

    @Test
    public void testCanAccessTestConfigurationViaSingleton() {
        assertThat(TestConfigManager.getInstance().getWebDriverConfig().getBaseUrl(),
                is("https://www.google.com"));
    }
}
