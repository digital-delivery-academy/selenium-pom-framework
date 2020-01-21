package uk.co.evoco.webdriver.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.MalformedURLException;
import java.net.URL;

public class GridConfig {
    private URL gridUrl;

    public URL getGridUrl() {
        return gridUrl;
    }

    @JsonProperty("url")
    public void setGridUrl(String gridUrl) throws MalformedURLException {
        this.gridUrl = new URL(gridUrl);
    }
}
