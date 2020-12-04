package uk.co.evoco.webdriver.configuration.utils;

import java.io.File;
import java.nio.file.Paths;

public class FileUtils {

    /**
     * Create require folder in target folder to download required files during test execution
     * @param filePath file path from config
     * @return absolute file download path
     */
    public static File createAndGetRequiredFolderInTargetFolder(String filePath) {
        File fileDownLoadDirectory = new File(Paths.get("target").toAbsolutePath()
                .toString().concat("/").concat(filePath));
        fileDownLoadDirectory.mkdir();
        return fileDownLoadDirectory;
    }
}
