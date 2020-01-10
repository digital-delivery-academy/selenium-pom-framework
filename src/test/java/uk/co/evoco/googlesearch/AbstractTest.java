package uk.co.evoco.googlesearch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import uk.co.evoco.webdriver.WebDriverBuilder;
import uk.co.evoco.webdriver.configuration.ConfigurationLoader;
import uk.co.evoco.webdriver.configuration.WebDriverConfig;
import uk.co.evoco.webdriver.results.ResultsManager;

import java.io.IOException;

public abstract class AbstractTest {
    protected EventFiringWebDriver webDriver;
    protected static WebDriverConfig webDriverConfig;
    protected static ResultsManager resultsManager;

    @BeforeAll
    public static void beforeAll() throws IOException {
        webDriverConfig = new ConfigurationLoader()
                .useConfigurationFile()
                .build();
        resultsManager = new ResultsManager();
        resultsManager.createScreenshotDirectory();
    }

    /**
     * This will run before EVERY @Test that extends this class
     * The method will create a new instance of WebDriver and a browser and open Google.com
     * This ensures we always have a fresh browser window and a guaranteed starting point
     */
    @BeforeEach
    public void setUp() {
        this.webDriver = new WebDriverBuilder()
                .setConfiguration(webDriverConfig)
                .setResultsDirectory(this.resultsManager.getScreenshotDirectory())
                .build();
        this.webDriver.get(webDriverConfig.getBaseUrl());
    }

    /**
     * This will run after EVERY @Test that extends this class
     * The method will close the current browser and WebDriver instance down
     * This ensures we have cleaned up after ourselves (this happens even if the test fails)
     */
    @AfterEach
    public void tearDown() {
        this.webDriver.quit();
    }

}
