package uk.co.evoco.webdriver;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.AxeReporter;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.io.IOException;
import java.util.List;

public class AccessibilityBase {
    private static final String reportPath = System.getProperty("user.dir") + "\\src\\test\\java\\reports\\";

    public static void checkAccessibilityViolations(WebDriver webDriver) {
        String reportFile = reportPath + "accessibilityReport";
        AxeBuilder builder = new AxeBuilder();
        Results results = builder.disableIframeTesting().analyze(webDriver, webDriver.findElement(By.xpath("//html")));
        saveReport(results, reportFile);
    }

    private static void saveReport(Results results, String reportFile) {
        List<Rule> violations = results.getViolations();
        if (violations.size() == 0) {
            Assert.assertTrue("No violations found", true);
        } else {
            AxeReporter.writeResultsToJsonFile(reportFile, results);
            Assert.assertEquals(0, violations.size());
        }
    }
}