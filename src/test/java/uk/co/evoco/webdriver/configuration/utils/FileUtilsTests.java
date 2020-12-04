package uk.co.evoco.webdriver.configuration.utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileUtilsTests {
    @Test
    public void testCreateAndGetTheFilePathInTargetFolder() {
        File folder = FileUtils.createAndGetRequiredFolderInTargetFolder("fileDownloads");
        String folderPath = Paths.get("target").toAbsolutePath().toString() + "/" + "fileDownloads";
        assertThat(folder.isDirectory(), is(true));
        assertThat(folderPath, is(folder.toString()));
    }
}
