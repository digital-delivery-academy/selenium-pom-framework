package uk.co.evoco.webdriver.utils;

import com.codahale.metrics.Timer;
import org.openqa.selenium.WebElement;
import uk.co.evoco.metrics.MetricRegistryHelper;
import uk.co.evoco.webdriver.configuration.TestConfigHelper;

import java.util.Optional;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * Only for us in the situations outlined for the provided methods.
 * There's nothing wrong with WebDrivers normal click method, if you don't need this, steer well clear.
 */
public final class ClickUtils extends TolerantInteraction {

    private static final Timer tolerantClickAction = MetricRegistryHelper.get().timer(name("ClickUtils.tolerantClick"));
    /**
     *
     * @param webElement active WebElements, already located
     * @param timeout time in seconds to keep trying
     * @throws Throwable any unhandled or un-tolerated exception
     */
    public static void tolerantClick(WebElement webElement, int timeout) throws Throwable {
        try(final Timer.Context ignored = tolerantClickAction.time()) {
            new ClickUtils().tolerantInteraction(webElement, Optional.empty(), timeout);
        }
    }

    public static void tolerantClick(WebElement webElement) throws Throwable {
        try(final Timer.Context ignored = tolerantClickAction.time()) {
            new ClickUtils().tolerantInteraction(webElement, Optional.empty(),
                    TestConfigHelper.get().getTolerantActionWaitTimeoutInSeconds());
        }
    }
}
