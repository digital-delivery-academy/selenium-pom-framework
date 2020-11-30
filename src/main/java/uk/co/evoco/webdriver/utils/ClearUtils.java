package uk.co.evoco.webdriver.utils;

import com.codahale.metrics.Timer;
import org.openqa.selenium.WebElement;
import uk.co.evoco.metrics.MetricRegistryHelper;
import uk.co.evoco.webdriver.configuration.TestConfigHelper;

import static com.codahale.metrics.MetricRegistry.name;

public class ClearUtils extends TolerantInteraction {

    private static final Timer tolerantClearAction = MetricRegistryHelper.get().timer(name("ClearUtils.tolerantClear"));

    private ClearUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     *
     * @param webElement active WebElement, already located
     * @param timeout time in seconds to keep trying
     * @throws Throwable any unhandled or un-tolerated exception
     */
    public static void tolerantClear(WebElement webElement, int timeout) throws Throwable {
        try(final Timer.Context ignored = tolerantClearAction.time()) {
            new ClearUtils().tolerantInteractionToClear(webElement, timeout);
        }
    }

    /**
     *
     * @param webElement active WebElement, already located
     * @throws Throwable any unhandled or un-tolerated exception
     */
    public static void tolerantClear(WebElement webElement) throws Throwable {
        try(final Timer.Context ignored = tolerantClearAction.time()) {
            new ClearUtils().tolerantInteractionToClear(webElement,
                    TestConfigHelper.get().getTolerantActionWaitTimeoutInSeconds());
        }
    }
}
