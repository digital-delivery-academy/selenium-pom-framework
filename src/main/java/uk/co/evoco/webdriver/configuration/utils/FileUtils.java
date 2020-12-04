package uk.co.evoco.webdriver.configuration.utils;

import java.io.File;
import java.nio.file.Paths;

public class FileUtils {

    /**
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
