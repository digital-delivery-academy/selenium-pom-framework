package uk.co.evoco.webdriver.utils;

import com.codahale.metrics.Timer;
import org.openqa.selenium.WebElement;
import uk.co.evoco.metrics.MetricRegistryHelper;
import uk.co.evoco.webdriver.configuration.TestConfigHelper;

import static com.codahale.metrics.MetricRegistry.name;

public class GetTextUtils extends TolerantInteraction {
    private static final Timer tolerantGetTextAction = MetricRegistryHelper.get().timer(name("GetTextUtils.tolerantGetText"));

    /**
     *
     * @param webElement active WebElement, already located
     * @param timeout time in seconds to keep trying
     * @return text property value
     * @throws Throwable any unhandled or un-tolerated exception
     */
    public static String tolerantGetText(WebElement webElement, int timeout) throws Throwable {
        try (final Timer.Context ignored = tolerantGetTextAction.time()) {
            return new GetTextUtils().tolerantInteractionToGetText(webElement,timeout);
        }
    }

    /**
     *
     * @param webElement active WebElement, already located
     * @return text property value
     * @throws Throwable any unhandled or un-tolerated exception
     */

    public static String tolerantGetText(WebElement webElement) throws Throwable {
        try (final Timer.Context ignored = tolerantGetTextAction.time()) {
            return new GetTextUtils().tolerantInteractionToGetText(webElement,
                    TestConfigHelper.get().getTolerantActionWaitTimeoutInSeconds());
        }
    }
}
