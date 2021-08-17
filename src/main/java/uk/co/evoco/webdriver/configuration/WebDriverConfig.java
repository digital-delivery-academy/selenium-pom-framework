package uk.co.evoco.webdriver.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.openqa.selenium.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

/**
 * A simple representation object for the "./src/test/resources/config.json" file
 */
public class WebDriverConfig {

    private BrowserType browserType;
    private URL baseUrl;
    private long webDriverWaitTimeout;
    private boolean isHeadless;
    private JsonNode testConfig;
    private GridConfig gridConfig;
    private RunType runType;
    private Map<String, ObjectNode> browserPreferences;
    private ObjectNode chromeLoggingPreferences;
    private TolerantActionExceptions tolerantActionExceptions;
    private MetricsConfig metricsConfig;
    private boolean takeScreenshotOnError;

    private final Logger logger = LoggerFactory.getLogger(WebDriverConfig.class);

    /**
     *
     * @return BrowserType for the run from the configuration file
     */
    public BrowserType getBrowserType() {
        return browserType;
    }

    /**
     *
     * @param browserType the browser for the run
     */
    @JsonProperty("browser")
    public void setBrowserType(String browserType) {
        this.browserType = BrowserType.valueOf(browserType.toUpperCase());
    }

    /**
     *
     * @param browserType the browser for the run
     */
    public void setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
    }

    /**
     *
     * @return the base URL for the application under test
     */
    public String getBaseUrl() {
        return baseUrl.toString();
    }

    /**
     * Aims to pull the baseUrl from the configuration file.  If, however, a system property has been set
     * via the command line, then this will be overridden and we will use the baseUrl that comes from the CLI.
     * This is important to ensure there is support for non-configuration dynamic configuration for CI systems
     * like Jenkins etc.
     * @param baseUrl the base URL for the application under test
     * @throws MalformedURLException if the base URL is not a valid URL
     */
    @JsonProperty("baseUrl")
    public void setBaseUrl(String baseUrl) throws MalformedURLException {
        String targetBaseUrl = System.getProperty("baseUrl", baseUrl);
        this.baseUrl = new URL(targetBaseUrl);
    }

    /**
     *
     * @return the timeout used in the framework for WebDriverWaits
     */
    public long getWebDriverWaitTimeout() {
        return webDriverWaitTimeout;
    }

    /**
     *
     * @param webDriverWaitTimeout the timeout used in the framework for WebDriverWaits
     */
    @JsonProperty("timeout")
    public void setWebDriverWaitTimeout(String webDriverWaitTimeout) {
        this.webDriverWaitTimeout = Long.parseLong(webDriverWaitTimeout);
    }

    /**
     *
     * @param item the name of the key for which the value you want to retrieve
     * @return String returns the target item that exists in the list of open options that can be passed in
     * the config file
     */
    public String getTestConfigItem(String item) {
        return this.testConfig.get(item).textValue();
    }

    /**
     *
     * @param testConfig the list of key/value pairs for the non-specific configuration items in the config file
     */
    @JsonProperty("testConfig")
    public void setTestConfig(JsonNode testConfig) {
        this.testConfig = testConfig;
    }

    /**
     *
     * @return boolean configuring headless running
     */
    public boolean isHeadless() {
        return isHeadless;
    }

    /**
     *
     * @param headless boolean configuring headless running
     */
    @JsonProperty("headless")
    public void setHeadless(boolean headless) {
        isHeadless = headless;
    }

    /**
     *
     * @return the GridConfig configuration
     */
    public GridConfig getGridConfig() {
        return gridConfig;
    }

    /**
     *
     * @param gridConfig the GridConfig configuration
     */
    @JsonProperty("gridConfig")
    public void setGridConfig(GridConfig gridConfig) {
        this.gridConfig = gridConfig;
    }

    /**
     *
     * @return the BrowserPreferences configuration
     */
    public ObjectNode getBrowserPreferences(BrowserType browserType) {
        return Optional.ofNullable(browserPreferences)
                .flatMap(options -> options.entrySet().stream()
                        .filter(entry -> entry.getKey().equalsIgnoreCase((browserType.toString())))
                        .findFirst()
                        .map(Map.Entry::getValue)
                )
                .orElse(JsonNodeFactory.instance.objectNode());
    }

    /**
     *
     * @return the ChromeLoggingPreferences configuration
     */
    public Optional<String> getChromeLoggingPreferences() {
        return Optional.ofNullable(chromeLoggingPreferences)
                .map(configObject -> configObject.retain("logLevel"))
                .filter(s -> !s.isEmpty())
                .map(logLevelNode -> logLevelNode.get("logLevel").asText())
                .stream()
                .findFirst();
    }

    /**
     *
     * @param browserPreferences the configuration properties for the various browsers supported by the webdriver
     */
    @JsonProperty("browserPreferences")
    public void setBrowserPreferences(Map<String, ObjectNode> browserPreferences) {
        this.browserPreferences = browserPreferences;
    }

    /**
     *
     * @param chromeLoggingPreferences the configuration logging level for the various browsers supported by the webdriver
     */
    @JsonProperty("chromeLoggingPreferences")
    public void setChromeLoggingPreferences(ObjectNode chromeLoggingPreferences) {
        this.chromeLoggingPreferences = chromeLoggingPreferences;
    }

    /**
     *
     * @return the run type
     */
    public RunType getRunType() {
        return runType;
    }

    /**
     *
     * @param runType the run type
     */
    @JsonProperty("runType")
    public void setRunType(String runType) {
        this.runType = RunType.valueOf(runType.toUpperCase());
    }

    /**
     *
     * @return the tolerant action exceptions config
     */
    public TolerantActionExceptions getTolerantActionExceptions() {
        return tolerantActionExceptions;
    }

    /**
     *
     */
    public void printToleratedExceptions() {
        StringBuilder stringBuilder = new StringBuilder("Exceptions that will be tolerated within Evoco framework when using tolerant actions:\n");
        for(String exception : tolerantActionExceptions.getExceptionsToHandle()) {
            stringBuilder
                    .append(exception)
                    .append("\n");
        }
        logger.debug(stringBuilder.toString());
    }

    /**
     *
     * @param tolerantActionExceptions set the tolerant action exceptions and tolerant action wait time in Seconds
     */
    @JsonProperty("tolerantActionExceptions")
    public void setTolerantActionExceptions(TolerantActionExceptions tolerantActionExceptions) {
        this.tolerantActionExceptions = tolerantActionExceptions;
    }

    /**
     *
     * @return tolerant action wait time in seconds if user specify the time in config file
     * other wise return default webdriver time out
     */
    public int getTolerantActionWaitTimeoutInSeconds() {
        return Optional.ofNullable(tolerantActionExceptions.getWaitTimeoutInSeconds())
                .map(Integer::parseInt)
                .orElse((int) webDriverWaitTimeout);
    }

    @JsonProperty("metrics")
    public void setMetricsConfig(MetricsConfig metricsConfig) {
        this.metricsConfig = metricsConfig;
    }

    public MetricsConfig getMetricsConfig() {
        return metricsConfig;
    }

    /**
     *
     * @return takeScreenshotOnErrors boolean configuring for taking screenshots on Errors
     */
    public boolean isTakeScreenshotOnError() {
        return takeScreenshotOnError;
    }

    @JsonProperty("takeScreenshotOnError")
    public void setTakeScreenshotOnError(boolean takeScreenshotOnError) {
        this.takeScreenshotOnError = takeScreenshotOnError;
    }
}
