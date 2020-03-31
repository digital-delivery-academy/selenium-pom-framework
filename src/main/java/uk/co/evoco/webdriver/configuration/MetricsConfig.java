package uk.co.evoco.webdriver.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetricsConfig {

    private JmxConfig jmxConfig;
    private GraphiteConfig graphiteConfig;

    public JmxConfig getJmxConfig() {
        return jmxConfig;
    }

    @JsonProperty("jmx")
    public void setJmxConfig(JmxConfig jmxConfig) {
        this.jmxConfig = jmxConfig;
    }

    public GraphiteConfig getGraphiteConfig() {
        return graphiteConfig;
    }

    @JsonProperty("graphite")
    public void setGraphiteConfig(GraphiteConfig graphiteConfig) {
        this.graphiteConfig = graphiteConfig;
    }
}
