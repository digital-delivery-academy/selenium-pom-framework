package uk.co.evoco.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import uk.co.evoco.webdriver.WebDriverBuilder;
import uk.co.evoco.webdriver.configuration.TestConfigManager;
import uk.co.evoco.webdriver.results.ResultsManager;

import java.io.IOException;

/**
 * BaseAbstractTest to handle things that testers shouldn't need to worry about (like starting up a WebDriver instance
 * and getting everything in the right space for consumers to run tests that are correctly configured etc).
 */
public abstract class BaseAbstractTest {
    protected EventFiringWebDriver webDriver;
    protected static ResultsManager resultsManager;

    /**
     * This will run before every test class.
     * This method gets the configuration and constructs the WebDriver instance, the screenshot
     * directory and the makes these items accessible.
     */
    @BeforeAll
    public static void beforeAll() {
        resultsManager = new ResultsManager();
        resultsManager.createScreenshotDirectory();
    }

    /**
     * This will run before EVERY @Test that extends this class
     * The method will create a new instance of WebDriver and a browser and open Google.com
     * This ensures we always have a fresh browser window and a guaranteed starting point
     * @throws IOException if results directory isn't created or config file cannot be found
     */
    @BeforeEach
    public void setUp() throws IOException {
        this.webDriver = new WebDriverBuilder()
                .setResultsDirectory(this.resultsManager.getScreenshotDirectory())
                .build();
        this.webDriver.get(TestConfigManager.get().getBaseUrl());
        this.webDriver.manage().window().maximize();
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
