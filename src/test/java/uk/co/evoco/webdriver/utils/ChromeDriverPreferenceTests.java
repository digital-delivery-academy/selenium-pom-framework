package uk.co.evoco.webdriver.utils;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.powermock.api.mockito.PowerMockito;
import uk.co.evoco.webdriver.configuration.TestConfigHelper;
import uk.co.evoco.webdriver.configuration.driver.ConfiguredChromeDriver;

import java.io.File;
import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChromeDriverPreferenceTests {

    private static String baseUrl;
    private static EmbeddedJetty embeddedJetty;
    private WebDriver webDriver;

    @BeforeEach
    public void webDriverSetup() throws Exception {
        embeddedJetty = new EmbeddedJetty();
        embeddedJetty.start();
        baseUrl = "http://localhost:" + embeddedJetty.getPort() + "/index.html";
    }

    @Test
    public void testChromeBrowserPreferencesApplied() throws Exception {
        System.setProperty("config", "fixtures/sample-config-with-chrome-preferences.json");
        webDriver = new ConfiguredChromeDriver().getDriver(FileUtils.getTempDirectory());
        webDriver.get(baseUrl);
        String expectedFile = new File("run-generated-files/chrome/downloads").getCanonicalPath() + "/sampleFile.pdf";
        webDriver.findElement(By.xpath("//a[text()='clickHereToDownLoadAFile']")).click();
        Thread.sleep(5000);//need to wait until file download
        assertThat("Expected downloaded file to exist in run-generated-files/chrome/downloads",
                new File(expectedFile).exists(), is(true));
    }

    @Test
    public void testChromeBrowserLoggingPreferencesApplied() throws Exception {
        Field testConfigHelperStaticVariable = PowerMockito.field(TestConfigHelper.class, "testConfigHelper");
        testConfigHelperStaticVariable.set(TestConfigHelper.class, null);

        System.setProperty("config", "fixtures/sample-config-with-chrome-logging-preferences.json");
        assertThat(System.getProperty("config"), is("fixtures/sample-config-with-chrome-logging-preferences.json"));
        webDriver = new ConfiguredChromeDriver().getDriver(FileUtils.getTempDirectory());
        webDriver.get(baseUrl);
        webDriver.findElement(By.xpath("//a[text()='clickHereToDownLoadAFile']"));
        try {
            Assertions.assertTrue(webDriver.manage().logs().get(LogType.PERFORMANCE).getAll().size() > 0);
        } catch (InvalidArgumentException e) {
            Assertions.fail("There are no Performance Logs that have been found");
        }
    }


    @AfterEach
    public void tearDown() throws Exception {
        this.webDriver.quit();
        System.setProperty("config", "DEFAULT");
        embeddedJetty.stop();
    }
}
