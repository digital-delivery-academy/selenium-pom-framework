package uk.co.evoco.webdriver.utils;

import com.codahale.metrics.Timer;
import org.openqa.selenium.WebElement;
import uk.co.evoco.metrics.MetricRegistryHelper;
import uk.co.evoco.webdriver.configuration.TestConfigHelper;

import java.util.Optional;

import static com.codahale.metrics.MetricRegistry.name;

public class IsDisplayedUtils extends TolerantInteraction {

    private static final Timer tolerantIsDisplayedAction = MetricRegistryHelper.get().timer(name("IsDisplayedUtils.tolerantIsDisplayed"));

    /**
     *
     * @param webElement active WebElement, already located
     * @param timeout time in seconds to keep trying
     * @return true if this element is present on the DOM false otherwise.
     * @throws Throwable any unhandled or un-tolerated exception
     */
    public static boolean tolerantIsDisplayed(WebElement webElement, int timeout) throws Throwable {
        try (final Timer.Context ignored = tolerantIsDisplayedAction.time()) {
            return new IsDisplayedUtils().tolerantInteraction(webElement, Optional.empty(), timeout).isDisplayed();
        }
    }

    /**
     *
     * @param webElement active WebElement, already located
     * @return true if this element is present on the DOM false otherwise.
     * @throws Throwable any unhandled or un-tolerated exception
     */
    public static boolean tolerantIsDisplayed(WebElement webElement) throws Throwable {
        try (final Timer.Context ignored = tolerantIsDisplayedAction.time()) {
            return new IsDisplayedUtils().tolerantInteraction(webElement, Optional.empty(),
                    TestConfigHelper.get().getTolerantActionWaitTimeoutInSeconds()).isDisplayed();
        }
    }
}
