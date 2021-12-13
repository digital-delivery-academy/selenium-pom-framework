package uk.co.evoco.tests;

import com.google.common.io.BaseEncoding;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import uk.co.evoco.webdriver.WebDriverBuilder;
import uk.co.evoco.webdriver.configuration.TestConfigHelper;
import uk.co.evoco.webdriver.results.ResultsManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * BaseAbstractTest to handle things that testers shouldn't need to worry about (like starting up a WebDriver instance
 * and getting everything in the right space for consumers to run tests that are correctly configured etc).
 */
public abstract class BaseAbstractTest {

    private static final Logger logger = LogManager.getLogger(BaseAbstractTest.class);
    private String testId;
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
        TestConfigHelper.get().printToleratedExceptions();
    }

    /**
     * This will run before EVERY @Test that extends this class
     * The method will create a new instance of WebDriver and a browser and open Google.com
     * This ensures we always have a fresh browser window and a guaranteed starting point
     * @throws IOException if results directory isn't created or config file cannot be found
     */
    @BeforeEach
    public void setUp() throws IOException {
        this.testId = generateTestId();
        this.webDriver = new WebDriverBuilder()
                .setResultsDirectory(resultsManager.getScreenshotDirectory())
                .build();
        this.webDriver.get(TestConfigHelper.get().getBaseUrl());
        this.webDriver.manage().window().maximize();
    }

    /**
     * This will run after EVERY @Test that extends this class
     * The method will close the current browser and WebDriver instance down
     * This ensures we have cleaned up after ourselves (this happens even if the test fails)
     */
    @AfterEach
    public void tearDown() {
        logger.debug("Test correlation ID: {}", testId);
        this.webDriver.quit();
    }

    /**
     * Provides a test ID that can be used to identify the test
     * @return
     */
    public String getTestId() {
        return this.testId;
    }

    private String generateTestId() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        String testId = StringUtils.replace(BaseEncoding.base64Url().encode(bb.array()), "=", "").toUpperCase();
        logger.debug("Test Correlation ID is: {}", testId);
        return testId;
    }
}
