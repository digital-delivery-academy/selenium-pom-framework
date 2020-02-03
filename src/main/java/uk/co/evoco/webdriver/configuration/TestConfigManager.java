package uk.co.evoco.webdriver.configuration;

/**
 *
 */
public class TestConfigManager {

    private static TestConfigManager testConfigManager;
    private static WebDriverConfig webDriverConfig;

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
    public static WebDriverConfig get() {
        if(null == testConfigManager) {
            testConfigManager = new TestConfigManager();
        }
        return webDriverConfig;
    }
}
