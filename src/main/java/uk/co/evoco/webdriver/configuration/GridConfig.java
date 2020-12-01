package uk.co.evoco.webdriver.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.MalformedURLException;
import java.net.URL;

public class GridConfig {

    private URL gridUrl;

    /**
     *
     * @return URL representing the grid address
     */
    public URL getGridUrl() {
        return gridUrl;
    }

    /**
     *
     * @param gridUrl a string representing the grid URL from the configuration file
     * @throws MalformedURLException if the URL passed in the configuration file is not a valid URL
     */
    @JsonProperty("url")
    public void setGridUrl(String gridUrl) throws MalformedURLException {
        this.gridUrl = new URL(gridUrl);
    }
}
