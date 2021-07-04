package uk.co.evoco.webdriver.utils;

import com.codahale.metrics.Timer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.evoco.metrics.MetricRegistryHelper;

import java.util.List;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * Utilities class providing support methods for locating elements in more dynamic ways than the standard
 * out of the box location methods provided by Selenium
 */
public final class FindByUtils {

    private static final Logger logger = LoggerFactory.getLogger(FindByUtils.class);
    private static final Timer multipleLocatorMatchGetDisplayedAction = MetricRegistryHelper.get().timer(name("FindByUtils.multipleLocatorMatchGetDisplayedAction"));
    private static final Timer byIdOrNameAction = MetricRegistryHelper.get().timer(name("FindByUtils.byIdOrName"));

    private FindByUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Finds the first element that is displayed with given locator.
     * Useful for instances where there are multiple elements on the DOM with the same locator and one (or more)
     * of them are hidden (e.g. used in mobile layouts or hold a different z-index given some other element is present)
     *
     * @param webDriver active WebDriver instance
     * @param locator   selector for target WebElement
     * @return active WebElement, already located
     */
    public static WebElement multipleLocatorMatchGetDisplayed(WebDriver webDriver, By locator)
            throws WebDriverException {
        try (final Timer.Context ignored = multipleLocatorMatchGetDisplayedAction.time()) {
            List<WebElement> elements = webDriver.findElements(locator);
            logger.info("Found {} elements with locator: {}", elements.size(), locator.toString());
            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    return element;
                }
            }
            throw new WebDriverException("No elements in the list were displayed");
        }
    }

    /**
     * Finds elements by either the HTML name or id attribute using the given selector text
     *
     * @param webDriver active WebDriver instance
     * @param idOrName  the variable locator
     * @return active WebElement, already located
     */
    public static WebElement byIdOrName(WebDriver webDriver, String idOrName) {
        try (final Timer.Context ignored = byIdOrNameAction.time()) {
            return webDriver.findElement(new ByIdOrName(idOrName));
        }
    }
}