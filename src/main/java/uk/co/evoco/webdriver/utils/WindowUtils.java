package uk.co.evoco.webdriver.utils;

import com.codahale.metrics.Timer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import uk.co.evoco.metrics.MetricRegistryHelper;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * Handles window utilities (like scrolling etc)
 */
public final class WindowUtils {

    private static final Timer scrollIntoViewAction = MetricRegistryHelper.get().timer(name("WindowUtils.scrollIntoView"));


    /**
     * Scrolls a given element into the Viewport view
     * @param webDriver active WebDriver instance
     * @param webElement active WebElement, already located
     */
    public static void scrollIntoView(WebDriver webDriver, WebElement webElement) {
        try(final Timer.Context ignored = scrollIntoViewAction.time()) {
            JavaScriptUtils.executeString(webDriver, webElement, "arguments[0].scrollIntoView(true);");
        }
    }
}
