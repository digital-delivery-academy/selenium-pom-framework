package uk.co.evoco.webdriver.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.MalformedURLException;
import java.net.URL;

public class GraphiteConfig {
    private boolean isEnabled;
    private URL hostUrl;

    public boolean isEnabled() {
        return isEnabled;
    }

    @JsonProperty("enabled")
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public URL getHostUrl() {
        return hostUrl;
    }

    @JsonProperty("host")
    public void setHostUrl(String hostUrl) throws MalformedURLException {
        this.hostUrl = new URL(hostUrl);
    }
}
