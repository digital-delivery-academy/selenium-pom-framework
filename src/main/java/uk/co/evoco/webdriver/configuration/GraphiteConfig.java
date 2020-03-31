package uk.co.evoco.webdriver.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GraphiteConfig {
    private boolean isEnabled;
    private String host;
    private int port;

    public boolean isEnabled() {
        return isEnabled;
    }

    @JsonProperty("enabled")
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getHost() {
        return host;
    }

    @JsonProperty("host")
    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    @JsonProperty("port")
    public void setPort(int port) {
        this.port = port;
    }
}
