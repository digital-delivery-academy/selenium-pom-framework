package uk.co.evoco.webdriver.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JmxConfig {

    private boolean isEnabled = false;

    public boolean isEnabled() {
        return isEnabled;
    }

    @JsonProperty("enabled")
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
