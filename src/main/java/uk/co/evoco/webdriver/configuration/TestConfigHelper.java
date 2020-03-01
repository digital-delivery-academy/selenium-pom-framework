package uk.co.evoco.webdriver.configuration;

/**
 *
 */
public class TestConfigHelper {

    private static TestConfigHelper testConfigHelper;
    private static WebDriverConfig webDriverConfig;

    /**
     *
     */
    private TestConfigHelper() {
        this.webDriverConfig = new ConfigurationLoader()
                .decideWhichConfigurationToUse()
                .build();
    }

    /**
     *
     * @return TestConfigManager returns the instance of the singleton to access the member methods
     */
    public static WebDriverConfig get() {
        if(null == testConfigHelper) {
            testConfigHelper = new TestConfigHelper();
        }
        return webDriverConfig;
    }
}
