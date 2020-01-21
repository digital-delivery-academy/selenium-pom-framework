package uk.co.evoco.webdriver.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Utilities class providing support methods for executing JavaScript
 * While this is straight forward, its less than obvious to newcomers.
 * Feel free not to use this at all and just do it with WebDriver!
 */
public final class JavaScriptUtils {

    /**
     * Executes a given JavaScript script
     * @param webDriver
     * @param javascript
     */
    public static void executeString(WebDriver webDriver, String javascript) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        javascriptExecutor.executeScript(javascript);
    }

    /**
     * Executes a given JavaScript script against a given WebElement
     * @param webDriver
     * @param webElement
     * @param javascript
     */
    public static void executeString(WebDriver webDriver, WebElement webElement, String javascript) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        javascriptExecutor.executeScript(javascript, webElement);
    }

    /**
     * Reads a file from the classpath (./src/test/resources/javascript/myfile.js) and executes it via the JavaScript
     * executor.
     * This can make tests a little more readable as there's no inline JavaScript and horrible escape characters to
     * content with.
     * @param webDriver
     * @param filename
     * @throws IOException
     */
    public static void executeFile(WebDriver webDriver, String filename) throws IOException {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        String javascript = FileUtils.readFileToString(
                new File(ClassLoader.getSystemResource(filename).getFile()),
                Charset.forName("UTF-8"));
        javascriptExecutor.executeScript(javascript);
    }
}
