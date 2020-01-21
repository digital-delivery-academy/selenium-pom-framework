package uk.co.evoco.webdriver.configuration;

public class TestConfigManager {

    private static TestConfigManager testConfigManager;
    private WebDriverConfig webDriverConfig;

    private TestConfigManager() {
        this.webDriverConfig = new ConfigurationLoader()
                .decideWhichConfigurationToUse()
                .build();
    }

    public static TestConfigManager getInstance() {
        if(null == testConfigManager) {
            testConfigManager = new TestConfigManager();
        }
        return testConfigManager;
    }

    public WebDriverConfig getWebDriverConfig() {
        return webDriverConfig;
    }
}
