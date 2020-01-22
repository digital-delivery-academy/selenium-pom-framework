package uk.co.evoco.webdriver.configuration;

/**
 *
 */
public class TestConfigManager {

    private static TestConfigManager testConfigManager;
    private WebDriverConfig webDriverConfig;

    /**
     *
     */
    private TestConfigManager() {
        this.webDriverConfig = new ConfigurationLoader()
                .decideWhichConfigurationToUse()
                .build();
    }

    /**
     *
     * @return TestConfigManager returns the instance of the singleton to access the member methods
     */
    public static TestConfigManager getInstance() {
        if(null == testConfigManager) {
            testConfigManager = new TestConfigManager();
        }
        return testConfigManager;
    }

    /**
     *
     * @return provides the globally available WebDriverConfig
     */
    public WebDriverConfig getWebDriverConfig() {
        return webDriverConfig;
    }
}
