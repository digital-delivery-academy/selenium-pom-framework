package uk.co.evoco.webdriver.configuration.utils;

import org.junit.jupiter.api.Test;
import uk.co.evoco.webdriver.configuration.WebDriverConfig;
import uk.co.evoco.webdriver.utils.JsonUtils;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileLoaderUtilsTests {

    @Test
    public void testCanLoadConfigFileFromClasspath() throws IOException {
        File file = FileLoaderUtils.loadFromClasspathOrFileSystem("fixtures/sample-config.json");
        WebDriverConfig webDriverConfig = JsonUtils.fromFile(file, WebDriverConfig.class);
        assertThat(file, instanceOf(File.class));
        assertThat(webDriverConfig.getBaseUrl(), is("https://www.google.com"));
    }

    @Test
    public void testCanLoadConfigFileFromPath() throws IOException {
        File file = FileLoaderUtils.loadFromClasspathOrFileSystem("./config-fs.json");
        WebDriverConfig webDriverConfig = JsonUtils.fromFile(file, WebDriverConfig.class);
        assertThat(file, instanceOf(File.class));
        assertThat(webDriverConfig.getBaseUrl(), is("https://www.yahoo.com"));
    }
}
