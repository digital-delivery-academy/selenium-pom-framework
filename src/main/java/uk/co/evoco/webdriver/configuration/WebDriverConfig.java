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

    /**
     * Aims to pull the baseUrl from the configuration file.  If, however, a system property has been set
     * via the command line, then this will be overridden and we will use the baseUrl that comes from the CLI.
     * This is important to ensure there is support for non-configuration dynamic configuration for CI systems
     * like Jenkins etc.
     * @param baseUrl
     * @throws MalformedURLException
     */
    @JsonProperty("baseUrl")
    public void setBaseUrl(String baseUrl) throws MalformedURLException {
        String targetBaseUrl = System.getProperty("baseUrl", baseUrl);
        this.baseUrl = new URL(targetBaseUrl);
    }

    public long getWebDriverWaitTimeout() {
        return webDriverWaitTimeout;
    }

    @JsonProperty("timeout")
    public void setWebDriverWaitTimeout(String webDriverWaitTimeout) {
        this.webDriverWaitTimeout = Long.parseLong(webDriverWaitTimeout);
    }
}
