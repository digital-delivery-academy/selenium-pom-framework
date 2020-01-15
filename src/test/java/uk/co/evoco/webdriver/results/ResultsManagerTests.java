package uk.co.evoco.webdriver.results;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResultsManagerTests {

    private ResultsManager resultsManager;
    private File screenshotDirectory;

    @BeforeEach
    public void setUp() {
        this.resultsManager = new ResultsManager();
        this.screenshotDirectory = resultsManager.createScreenshotDirectory();
    }

    @Test
    public void testCanCreateScreenshotDirectoryWithTimestamp() {
        assertThat(this.screenshotDirectory.isDirectory(), is(true));
    }

    @Test
    public void testCanCreateScreenshotDirectoryWithTimestampUsingResultsManager() {
        assertThat(this.resultsManager.getScreenshotDirectory().isDirectory(), is(true));
    }

    @AfterEach
    public void tidyUp() {
        screenshotDirectory.delete();
    }
}
