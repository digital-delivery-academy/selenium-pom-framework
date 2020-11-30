package uk.co.evoco.webdriver.utils;

import com.codahale.metrics.Timer;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import uk.co.evoco.metrics.MetricRegistryHelper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * Utilities class providing support methods for executing JavaScript
 * While this is straight forward, its less than obvious to newcomers.
 * Feel free not to use this at all and just do it with WebDriver!
 */
public final class JavaScriptUtils {

    private static final Timer executeStringAction = MetricRegistryHelper.get().timer(name("JavaScriptUtils.executeString"));
    private static final Timer executeFileAction = MetricRegistryHelper.get().timer(name("JavaScriptUtils.executeFile"));

    private JavaScriptUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Executes a given JavaScript script
     * @param webDriver active WebDriver instance
     * @param javascript String representing javascript expression
     */
    public static void executeString(WebDriver webDriver, String javascript) {
        try(final Timer.Context ignored = executeStringAction.time()) {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
            javascriptExecutor.executeScript(javascript);
        }
    }

    /**
     * Executes a given JavaScript script against a given WebElement
     * @param webDriver active WebDriver instance
     * @param webElement active WebElement, already located
     * @param javascript String representing javascript expression
     */
    public static void executeString(WebDriver webDriver, WebElement webElement, String javascript) {
        try(final Timer.Context ignored = executeStringAction.time()) {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
            javascriptExecutor.executeScript(javascript, webElement);
        }
    }

    /**
     * Reads a file from the classpath (./src/test/resources/javascript/myfile.js) and executes it via the JavaScript
     * executor.
     * This can make tests a little more readable as there's no inline JavaScript and horrible escape characters to
     * content with.
     * @param webDriver active WebDriver instance
     * @param filename File representing javascript expression
     * @throws IOException if the file cannot be found
     */
    public static void executeFile(WebDriver webDriver, String filename) throws IOException {
        try(final Timer.Context ignored = executeFileAction.time()) {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
            String javascript = FileUtils.readFileToString(
                    new File(ClassLoader.getSystemResource(filename).getFile()),
                    Charset.forName("UTF-8"));
            javascriptExecutor.executeScript(javascript);
        }
    }
}
