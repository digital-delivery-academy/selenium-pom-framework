package uk.co.evoco.webdriver.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
        WebDriverConfig webDriverConfig = JsonUtils.fromFile(
                ClassLoader.getSystemResourceAsStream("fixtures/sample-config.json"), WebDriverConfig.class);
        assertThat(webDriverConfig.getBaseUrl(), is("https://www.google.com"));
        assertThat(webDriverConfig.getBrowserType(), is(BrowserType.CHROME));
        assertThat(webDriverConfig.getWebDriverWaitTimeout(), is(30L));
        assertThat(webDriverConfig.getTestConfigItem("sample"), is("sample text"));
        assertThat(webDriverConfig.getRunType(), is(RunType.LOCAL));
        assertThat(webDriverConfig.getGridConfig().getGridUrl().toString(), is("http://localhost:4444/wd/hub"));
    }

    @Test
    public void testGetBrowserPreferencesReturnsAnEmptyNodeIfNoBrowserOptionsArePresentInConfigJson() throws JsonProcessingException {
        String configJson = "{}";
        WebDriverConfig webDriverConfig = JsonUtils.fromString(configJson, WebDriverConfig.class);

        JsonNode actualPreferences = webDriverConfig.getBrowserPreferences(BrowserType.CHROME);
        assertThat(actualPreferences, is(JsonNodeFactory.instance.objectNode()));
    }

    @Test
    public void testGetBrowserPreferencesReturnsAnEmptyNodeIfSpecifiedBrowserTypeIsNotPresentInConfigJson() throws JsonProcessingException {
        String configJson = "{ \"browserPreferences\": {}}";
        WebDriverConfig webDriverConfig = JsonUtils.fromString(configJson, WebDriverConfig.class);

        JsonNode actualPreferences = webDriverConfig.getBrowserPreferences(BrowserType.CHROME);
        assertThat(actualPreferences, is(JsonNodeFactory.instance.objectNode()));
    }

    @Test
    public void testGetBrowserPreferencesReturnsTheCorrectBrowserOptions() throws JsonProcessingException {
        String preferenceKey = "browser.download.dir";
        String preferenceValue = "docs/chrome/";
        String inputConfigJson = String.format("{ \"browserPreferences\": { \"chrome\": {\"%s\": \"%s\"}}}", preferenceKey, preferenceValue);

        WebDriverConfig webDriverConfig = JsonUtils.fromString(inputConfigJson, WebDriverConfig.class);
        JsonNode actualPreferences = webDriverConfig.getBrowserPreferences(BrowserType.CHROME);

        ObjectNode expectedPreferences = JsonNodeFactory.instance.objectNode()
                .put(preferenceKey, preferenceValue);
        assertThat(actualPreferences, is(expectedPreferences));
    }

    @Test
    public void testGetBrowserPropertiesReturnsTheCorrectBrowserOptionsIrrespectiveOfCase() throws JsonProcessingException {
        String preferenceKey = "browser.download.dir";
        String preferenceValue = "docs/chrome/";
        String inputConfigJson = String.format("{ \"browserPreferences\": { \"CHROME\": {\"%s\": \"%s\"}}}", preferenceKey, preferenceValue);

        WebDriverConfig webDriverConfig = JsonUtils.fromString(inputConfigJson, WebDriverConfig.class);
        JsonNode actualPreferences = webDriverConfig.getBrowserPreferences(BrowserType.CHROME);

        ObjectNode expectedPreferences = JsonNodeFactory.instance.objectNode()
                .put(preferenceKey, preferenceValue);
        assertThat(actualPreferences, is(expectedPreferences));
    }

    @Test
    public void testConstructionFromJsonFileWithBadBaseUrlFails() {
        assertThrows(JsonMappingException.class, () -> {
            JsonUtils.fromFile(
                    ClassLoader.getSystemResourceAsStream("fixtures/sample-config-with-bad-base-url.json"),
                    WebDriverConfig.class);
        });
    }

    @Test
    public void testSettingBaseUrlWithBadUrlGivesGoodException() {
        WebDriverConfig webDriverConfig = new WebDriverConfig();
        assertThrows(MalformedURLException.class, () -> {
            webDriverConfig.setBaseUrl("bad-url");
        });
    }

    @Test
    public void testExceptionsWaitTimeOutReturnValue() throws IOException {
        WebDriverConfig webDriverConfig = JsonUtils.fromFile(
                ClassLoader.getSystemResourceAsStream("fixtures/sample-config-with-tolerant-action-wait-time.json")
                , WebDriverConfig.class);
        assertThat(webDriverConfig.getTolerantActionWaitTimeoutInSeconds(), is(5));

    }

    @Test
    public void testExceptionsWaitTimeOutNotReturnValue() throws IOException {
        WebDriverConfig webDriverConfig = JsonUtils.fromFile(
                ClassLoader.getSystemResourceAsStream("fixtures/sample-config-with-out-tolerant-action-wait-time.json")
                , WebDriverConfig.class);
        assertThat(webDriverConfig.getTolerantActionWaitTimeoutInSeconds(), is(30));

    }
}
