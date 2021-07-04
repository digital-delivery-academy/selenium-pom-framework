package uk.co.evoco.webdriver;

import org.junit.jupiter.api.*;
import uk.co.evoco.tests.BaseAbstractTest;
import uk.co.evoco.webdriver.utils.EmbeddedJetty;
import java.io.File;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AccessibilityTest extends BaseAbstractTest{

    private static String baseUrl;
    private static EmbeddedJetty embeddedJetty;
    private static final String reportPath = System.getProperty("user.dir") + "\\src\\test\\java\\reports\\accessibilityReport.json";
    private final File accessibilityReport = new File(reportPath);


    @BeforeAll
    public static void webDriverSetup() throws Exception {
        embeddedJetty = new EmbeddedJetty();
        embeddedJetty.start();
        String landingPage = "/index.html";
        baseUrl = "http://localhost:" + embeddedJetty.getPort() + landingPage;
    }

    @AfterAll
    public static void webDriverTearDown() throws Exception {
        embeddedJetty.stop();
    }

    @BeforeEach
    public void setUpWebApp() {
        webDriver.get(baseUrl);
    }

    @AfterEach
    public void deleteReport() {
        accessibilityReport.delete();
    }

    @Test
    public void testPageWithNoAccessibilityViolations() throws IOException {
        AccessibilityBase.checkAccessibilityViolations(webDriver);
        assertThat(accessibilityReport.exists(), is(false));
    }

    //Add assertion to read the json file output to check it has found the violations
    //Add html page that has accessibility violations
    @Test
    public void testPageWithAccessibilityViolations() throws IOException {
        webDriver.navigate().to(baseUrl + "/accessibilityViolations.html");
        AccessibilityBase.checkAccessibilityViolations(webDriver);
        assertThat(accessibilityReport.exists(), is(true));
    }
}
