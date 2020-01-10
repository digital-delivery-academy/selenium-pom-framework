package uk.co.evoco.webdriver.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple representation object for the "./src/test/resources/config.json" file
 */
public class WebDriverConfig {

    private BrowserType browserType;
    private URL baseUrl;
    private long webDriverWaitTimeout;

    public BrowserType getBrowserType() {
        return browserType;
    }

    @JsonProperty("browser")
    public void setBrowserType(String browserType) {
        this.browserType = BrowserType.valueOf(browserType.toUpperCase());
    }

    public void setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
    }

    public String getBaseUrl() {
        return baseUrl.toString();
    }

    @JsonProperty("baseUrl")
    public void setBaseUrl(String baseUrl) throws MalformedURLException {
        this.baseUrl = new URL(baseUrl);
    }

    public long getWebDriverWaitTimeout() {
        return webDriverWaitTimeout;
    }

    @JsonProperty("timeout")
    public void setWebDriverWaitTimeout(String webDriverWaitTimeout) {
        this.webDriverWaitTimeout = Long.parseLong(webDriverWaitTimeout);
    }
}
