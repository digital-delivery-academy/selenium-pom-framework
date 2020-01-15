package uk.co.evoco.webdriver.configuration;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.jupiter.api.Test;
import uk.co.evoco.webdriver.utils.JsonUtils;

import java.io.IOException;
import java.net.MalformedURLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WebDriverConfigTests {

    @Test
    public void testCanCreateInstanceFromJsonFileAndTestGetters() throws IOException {
        WebDriverConfig webDriverConfig = JsonUtils.fromFile(ClassLoader.getSystemResourceAsStream("fixtures/sample-config.json"), WebDriverConfig.class);
        assertThat(webDriverConfig.getBaseUrl(), is("https://www.google.com"));
        assertThat(webDriverConfig.getBrowserType(), is(BrowserType.CHROME));
        assertThat(webDriverConfig.getWebDriverWaitTimeout(), is(30L));
    }

    @Test
    public void testConstructionFromJsonFileWithBadBaseUrlFails() {
        assertThrows(JsonMappingException.class, () -> {
            JsonUtils.fromFile(ClassLoader.getSystemResourceAsStream("fixtures/sample-config-with-bad-base-url.json"), WebDriverConfig.class);
        });
    }

    @Test
    public void testSettingBaseUrlWithBadUrlGivesGoodException() {
        WebDriverConfig webDriverConfig = new WebDriverConfig();
        assertThrows(MalformedURLException.class, () -> {
            webDriverConfig.setBaseUrl("bad-url");
        });
    }
}
