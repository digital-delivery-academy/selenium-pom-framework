package uk.co.evoco.webdriver.results;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Responsible for sorting out folders and anything else to do with reporting
 */
public class ResultsManager {

    private SimpleDateFormat timestamp;
    private File screenshotDirectory;

    public ResultsManager() {
        this.timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    }

    /**
     * Creates a run specific directory that is timestamped to store screenshots
     * @throws IOException
     */
    public void createScreenshotDirectory() {
        this.screenshotDirectory = new File(Paths.get("target").toString() + "/screenshots-" + timestamp.format(new Date()));
        this.screenshotDirectory.mkdir();
    }

    /**
     *
     * @return File
     */
    public File getScreenshotDirectory() {
        return this.screenshotDirectory;
    }
}
