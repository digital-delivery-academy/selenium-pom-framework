package uk.co.evoco.webdriver.utils;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import uk.co.evoco.webdriver.configuration.driver.ConfiguredChromeDriver;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChromeDriverPreferenceTests {

    private static String baseUrl;
    private static EmbeddedJetty embeddedJetty;
    private WebDriver webDriver;

    @BeforeAll
    public static void webDriverSetup() throws Exception {
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
        assertThat(new File(expectedFile).exists(), is(true));
    }

    @AfterEach
    public void tearDown() {
        this.webDriver.quit();
    }

    @AfterAll
    public static void webDriverTearDown() throws Exception {
        System.setProperty("config", "DEFAULT");
        embeddedJetty.stop();
    }
}
