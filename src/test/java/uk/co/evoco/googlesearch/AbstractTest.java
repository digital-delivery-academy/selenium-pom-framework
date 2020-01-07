package uk.co.evoco.googlesearch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import uk.co.evoco.webdriver.WebDriverBuilder;

public abstract class AbstractTest {
    protected EventFiringWebDriver webDriver;

    /**
     * This will run before EVERY @Test that extends this class
     * The method will create a new instance of WebDriver and a browser and open Google.com
     * This ensures we always have a fresh browser window and a guaranteed starting point
     */
    @BeforeEach
    public void setUp() {
        this.webDriver = new WebDriverBuilder().build();
        this.webDriver.get("https://www.google.com");
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
