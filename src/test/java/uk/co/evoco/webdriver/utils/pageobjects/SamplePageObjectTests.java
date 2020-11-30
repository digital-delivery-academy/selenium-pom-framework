package uk.co.evoco.webdriver.utils.pageobjects;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import uk.co.evoco.tests.BaseAbstractTest;
import uk.co.evoco.webdriver.configuration.driver.ConfiguredChromeDriver;
import uk.co.evoco.webdriver.utils.EmbeddedJetty;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SamplePageObjectTests extends BaseAbstractTest {

    private static String baseUrl;
    private static EmbeddedJetty embeddedJetty;

    @BeforeAll
    public static void webDriverSetup() throws Exception {
        embeddedJetty = new EmbeddedJetty();
        embeddedJetty.start();
        baseUrl = "http://localhost:" + embeddedJetty.getPort() + "/index.html";
    }

    @Test
    public void testCanUseBasePageObject() throws IOException {
        WebDriver webDriver = new ConfiguredChromeDriver().getDriver(FileUtils.getTempDirectory());
        SamplePageObject samplePageObject = new SamplePageObject(webDriver);
        webDriver.get(baseUrl);
        samplePageObject.fillTextBox();
        assertThat("Text incorrect", samplePageObject.getTextBoxContents(), is("Hello from sample page object"));
    }
}
